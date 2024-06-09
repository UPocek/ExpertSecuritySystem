package com.ftn.sbnz.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.AggregationToStore;
import com.ftn.sbnz.model.models.Camera;
import com.ftn.sbnz.model.models.ContinuousSensor;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.models.Product;
import com.ftn.sbnz.model.models.ProductAggregationToStore;
import com.ftn.sbnz.model.models.Room;
import com.ftn.sbnz.model.models.Security;
import com.ftn.sbnz.repository.IAggregationsRepository;
import com.ftn.sbnz.repository.ICameraRepository;
import com.ftn.sbnz.repository.IContinuousSensorRepository;
import com.ftn.sbnz.repository.IProductAggregationsRepository;
import com.ftn.sbnz.repository.IProductRepository;
import com.ftn.sbnz.repository.IRoomRepository;
import com.ftn.sbnz.repository.ISecurityRepository;
import com.ftn.sbnz.template.KieSessionTemplates;
import com.ftn.sbnz.ws.SocketHandler;

import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SessionManager {
    private static HashMap<String, KieSession> sessions = new HashMap<>();

    private static HashMap<String, KieSession> reportSessions = new HashMap<>();

    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private IAggregationsRepository aggregationsRepository;

    @Autowired
    private IProductAggregationsRepository productAggregationsRepository;

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IContinuousSensorRepository continuousSensorRepository;

    @Autowired
    private ICameraRepository cameraRepository;

    @Autowired
    private ISecurityRepository securityRepository;

    public KieSession updateSession(String key, KieSession updatedSession) {

        sessions.put(key, updatedSession);

        return sessions.get(key);
    }

    public void updateSecuritySession() {
        List<ContinuousSensor> continuousSensor = continuousSensorRepository.findAll();

        if (!continuousSensor.isEmpty()) {
            KieHelper kieHelper = new KieHelper();
            String drlLow = KieSessionTemplates.addSensorLowToSession(continuousSensor);
            String drlMedium = KieSessionTemplates.addSensorMediumToSession(continuousSensor);
            String drlHigh = KieSessionTemplates.addSensorHighToSession(continuousSensor);

            InputStream ruleStream = this.getClass()
                    .getResourceAsStream("/rules/forward_security/forward.drl");
            String text = "";

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ruleStream))) {
                text = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            kieHelper.addContent(drlLow, ResourceType.DRL);
            kieHelper.addContent(drlMedium, ResourceType.DRL);
            kieHelper.addContent(drlHigh, ResourceType.DRL);
            kieHelper.addContent(text, ResourceType.DRL);

            KieSession newSession = kieHelper.build().newKieSession();

            if (sessions.get("ForwardSecSession") != null
                    && !sessions.get("ForwardSecSession").getObjects().isEmpty()) {
                for (Object fact : sessions.get("ForwardSecSession").getObjects()) {
                    newSession.insert(fact);
                }
            }

            updateSession("ForwardSecSession", newSession);
        }
    }

    public KieSession getSecuritySession() {
        var session = sessions.get("ForwardSecSession");
        if (session == null) {
            updateSecuritySession();
            session = sessions.get("ForwardSecSession");

            List<ContinuousSensor> continuousSensor = continuousSensorRepository.findAll();
            for (ContinuousSensor c : continuousSensor) {
                session.insert(c);
            }

            List<Room> rooms = roomRepository.findAll();
            for (Room r : rooms) {
                session.insert(r);
            }

            List<Camera> cameras = cameraRepository.findAll();
            for (Camera c : cameras) {
                session.insert(c);
            }

            List<Security> securities = securityRepository.findAll();
            for (Security s : securities) {
                session.insert(s);
            }

            return session;
        }

        return session;
    }

    public KieSession getConfigSession() {
        KieSession session = sessions.get("ForwardConfigSession");
        if (session == null) {
            session = kieContainer.newKieSession("ForwardConfigSession");
            sessions.put("ForwardConfigSession", session);
        }

        return session;
    }

    public KieSession getAggregatePeopleSession() {
        var session = sessions.get("cepPeopleSession");
        if (session == null) {
            session = kieContainer.newKieSession("cepPeopleSession");

            sessions.put("cepPeopleSession", session);
            session.setGlobal("aggregationRepository", aggregationsRepository);
        }

        return session;
    }

    public KieSession getAggregateProducteSession() {
        var session = sessions.get("cepProductSession");
        if (session == null) {
            session = kieContainer.newKieSession("cepProductSession");

            sessions.put("cepProductSession", session);
            session.setGlobal("productAggregatioinsRepository", productAggregationsRepository);
        }

        return session;
    }

    @Transactional
    public List<Room> getBottomLevelRooms() {
        List<Room> allRooms = roomRepository.findAll();
        return allRooms.stream()
                .filter(room -> roomRepository.findByIsContainedIn(room).isEmpty())
                .collect(Collectors.toList());
    }

    public KieSession getPeopleReportSession() {
        if (reportSessions.get("ReportSessionPeople") != null) {
            return reportSessions.get("ReportSessionPeople");
        }

        var reportSession = kieContainer.newKieSession("ReportSessionPeople");
        var rooms = roomRepository.findAll();
        for (Room r : rooms) {
            if (r.getIsContainedIn() == null) {
                reportSession.insert(new Location(r.getName(), r.getName()));
            } else {
                reportSession
                        .insert(new Location(r.getName(), r.getIsContainedIn().getName()));
            }
        }

        var aggregatedObjects = aggregationsRepository.findAll();
        for (AggregationToStore a : aggregatedObjects) {
            reportSession.insert(a.getAggregationDetection());
        }

        return reportSession;
    }

    public KieSession getProductReportSession() {
        if (reportSessions.get("ReportSessionProduct") != null) {
            return reportSessions.get("ReportSessionProduct");
        }

        var reportSession = kieContainer.newKieSession("ReportSessionProduct");
        var products = productRepository.findAll();
        for (Product p : products) {
            if (p.getIsContainedIn() == null) {
                reportSession.insert(new Location(p.getName(), p.getName()));
            } else {
                reportSession
                        .insert(new Location(p.getName(), p.getIsContainedIn().getName()));
            }
        }

        var aggregatedObjects = productAggregationsRepository.findAll();
        for (ProductAggregationToStore a : aggregatedObjects) {
            reportSession.insert(a.getAggregateProduct());
        }

        return reportSession;
    }

    @Transactional
    public List<Product> getBottomLevelProducts() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream()
                .filter(product -> productRepository.findByIsContainedIn(product).isEmpty())
                .collect(Collectors.toList());
    }

    // public KieSession getProductReportSession() {
    // if (reportSessions.get("ReportSessionProduct") != null) {
    // return reportSessions.get("ReportSessionProduct");
    // }

    // var reportSession = kieContainer.newKieSession("ReportSessionProduct");
    // var products = productRepository.findAll();
    // for (Product p : products) {
    // if (p.getIsContainedIn() == null) {
    // reportSession.insert(new Location(p.getName(), r.getName()));
    // } else {
    // reportSession
    // .insert(new Location(r.getName(), r.getIsContainedIn().getName()));
    // }
    // }

    // var aggregatedObjects = aggregationsRepository.findAll();
    // for (AggregationToStore a : aggregatedObjects) {
    // reportSession.insert(a.getAggregationDetection());
    // }

    // return reportSession;
    // }

    public void closeSession(String key) {
        var session = sessions.get(key);
        sessions.remove(key);

        session.dispose();
    }
}
