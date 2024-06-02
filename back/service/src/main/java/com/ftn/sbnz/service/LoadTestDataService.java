package com.ftn.sbnz.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.AggregatedDetection;
import com.ftn.sbnz.model.models.Aggregation;
import com.ftn.sbnz.model.models.AggregationToStore;
import com.ftn.sbnz.model.models.Room;
import com.ftn.sbnz.repository.IAggregationsRepository;
import com.ftn.sbnz.repository.IRoomRepository;

@Service
public class LoadTestDataService {
    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IAggregationsRepository aggregationsRepository;

    private Random random = new Random();

    @Transactional
    public void createRoomHierarchy() {
        // Create root room
        Room root = new Room("Root Room");
        root = roomRepository.save(root);

        // Create level 1 rooms
        Room level1Room1 = new Room("Level 1 Room 1");
        level1Room1.setIsContainedIn(root);
        level1Room1 = roomRepository.save(level1Room1);

        Room level1Room2 = new Room("Level 1 Room 2");
        level1Room2.setIsContainedIn(root);
        level1Room2 = roomRepository.save(level1Room2);

        // Create level 2 rooms
        Room level2Room1 = new Room("Level 2 Room 1");
        level2Room1.setIsContainedIn(level1Room1);
        level2Room1 = roomRepository.save(level2Room1);

        Room level2Room2 = new Room("Level 2 Room 2");
        level2Room2.setIsContainedIn(level1Room1);
        level2Room2 = roomRepository.save(level2Room2);

        Room level2Room3 = new Room("Level 2 Room 3");
        level2Room3.setIsContainedIn(level1Room2);
        level2Room3 = roomRepository.save(level2Room3);

        Room level2Room4 = new Room("Level 2 Room 4");
        level2Room4.setIsContainedIn(level1Room2);
        level2Room4 = roomRepository.save(level2Room4);

        // // Create level 3 rooms
        Room level3Room1 = new Room("Level 3 Room 1");
        level3Room1.setIsContainedIn(level2Room1);
        level3Room1 = roomRepository.save(level3Room1);

        Room level3Room2 = new Room("Level 3 Room 2");
        level3Room2.setIsContainedIn(level2Room1);
        level3Room2 = roomRepository.save(level3Room2);

        Room level3Room3 = new Room("Level 3 Room 3");
        level3Room3.setIsContainedIn(level2Room2);
        level3Room3 = roomRepository.save(level3Room3);

        Room level3Room4 = new Room("Level 3 Room 4");
        level3Room4.setIsContainedIn(level2Room2);
        level3Room4 = roomRepository.save(level3Room4);

        Room level3Room5 = new Room("Level 3 Room 5");
        level3Room5.setIsContainedIn(level2Room3);
        level3Room5 = roomRepository.save(level3Room5);

        Room level3Room6 = new Room("Level 3 Room 6");
        level3Room6.setIsContainedIn(level2Room3);
        level3Room6 = roomRepository.save(level3Room6);

        Room level3Room7 = new Room("Level 3 Room 7");
        level3Room7.setIsContainedIn(level2Room4);
        level3Room7 = roomRepository.save(level3Room7);

        Room level3Room8 = new Room("Level 3 Room 8");
        level3Room8.setIsContainedIn(level2Room4);
        level3Room8 = roomRepository.save(level3Room8);

        // // Create level 4 rooms
        // Room level4Room1 = new Room("Level 4 Room 1");
        // level4Room1.setIsContainedIn(level3Room1);
        // level4Room1 = roomRepository.save(level4Room1);

        // Room level4Room2 = new Room("Level 4 Room 2");
        // level4Room2.setIsContainedIn(level3Room1);
        // level4Room2 = roomRepository.save(level4Room2);

        // Room level4Room3 = new Room("Level 4 Room 3");
        // level4Room3.setIsContainedIn(level3Room2);
        // level4Room3 = roomRepository.save(level4Room3);

        // Room level4Room4 = new Room("Level 4 Room 4");
        // level4Room4.setIsContainedIn(level3Room2);
        // level4Room4 = roomRepository.save(level4Room4);

        // Room level4Room5 = new Room("Level 4 Room 5");
        // level4Room5.setIsContainedIn(level3Room3);
        // level4Room5 = roomRepository.save(level4Room5);

        // Room level4Room6 = new Room("Level 4 Room 6");
        // level4Room6.setIsContainedIn(level3Room3);
        // level4Room6 = roomRepository.save(level4Room6);

        // Room level4Room7 = new Room("Level 4 Room 7");
        // level4Room7.setIsContainedIn(level3Room4);
        // level4Room7 = roomRepository.save(level4Room7);

        // Room level4Room8 = new Room("Level 4 Room 8");
        // level4Room8.setIsContainedIn(level3Room4);
        // level4Room8 = roomRepository.save(level4Room8);

        // Room level4Room9 = new Room("Level 4 Room 9");
        // level4Room9.setIsContainedIn(level3Room5);
        // level4Room9 = roomRepository.save(level4Room9);

        // Room level4Room10 = new Room("Level 4 Room 10");
        // level4Room10.setIsContainedIn(level3Room5);
        // level4Room10 = roomRepository.save(level4Room10);

        // Room level4Room11 = new Room("Level 4 Room 11");
        // level4Room11.setIsContainedIn(level3Room6);
        // level4Room11 = roomRepository.save(level4Room11);

        // Room level4Room12 = new Room("Level 4 Room 12");
        // level4Room12.setIsContainedIn(level3Room6);
        // level4Room12 = roomRepository.save(level4Room12);

        // Room level4Room13 = new Room("Level 4 Room 13");
        // level4Room13.setIsContainedIn(level3Room7);
        // level4Room13 = roomRepository.save(level4Room13);

        // Room level4Room14 = new Room("Level 4 Room 14");
        // level4Room14.setIsContainedIn(level3Room7);
        // level4Room14 = roomRepository.save(level4Room14);

        // Room level4Room15 = new Room("Level 4 Room 15");
        // level4Room15.setIsContainedIn(level3Room8);
        // level4Room15 = roomRepository.save(level4Room15);

        // Room level4Room16 = new Room("Level 4 Room 16");
        // level4Room16.setIsContainedIn(level3Room8);
        // level4Room16 = roomRepository.save(level4Room16);

    }

    @Transactional
    public List<Room> getBottomLevelRooms() {
        List<Room> allRooms = roomRepository.findAll();
        return allRooms.stream()
                .filter(room -> roomRepository.findByIsContainedIn(room).isEmpty())
                .collect(Collectors.toList());
    }

    @Transactional
    public void createAggregationsForBottomLevelRooms() {
        List<Room> bottomLevelRooms = getBottomLevelRooms();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date oneWeekAgo = calendar.getTime();

        List<AggregationToStore> aggregationToStoreList = new ArrayList<>();

        for (Room room : bottomLevelRooms) {
            List<AggregatedDetection> fiveMinuteDetections = generateDetections(room, oneWeekAgo, new Date(), 5);

            List<AggregatedDetection> fifteenMinuteAggregations = createAggregations(fiveMinuteDetections, 15);
            List<AggregatedDetection> thirtyMinuteAggregations = createAggregations(fifteenMinuteAggregations, 30);
            List<AggregatedDetection> sixtyMinuteAggregations = createAggregations(thirtyMinuteAggregations, 60);
            List<AggregatedDetection> oneTwentyMinuteAggregations = createAggregations(sixtyMinuteAggregations, 120);
            List<AggregatedDetection> twoFortyMinuteAggregations = createAggregations(oneTwentyMinuteAggregations, 240);
            List<AggregatedDetection> fourEightyMinuteAggregations = createAggregations(twoFortyMinuteAggregations,
                    480);
            List<AggregatedDetection> oneFourtyFourtyMinuteAggregations = createAggregations(
                    fourEightyMinuteAggregations, 1440);

            // aggregationToStoreList.addAll(convertToAggregationToStore(fiveMinuteDetections));
            // aggregationToStoreList.addAll(convertToAggregationToStore(fifteenMinuteAggregations));
            aggregationToStoreList.addAll(convertToAggregationToStore(thirtyMinuteAggregations));
            aggregationToStoreList.addAll(convertToAggregationToStore(sixtyMinuteAggregations));
            aggregationToStoreList.addAll(convertToAggregationToStore(oneTwentyMinuteAggregations));
            aggregationToStoreList.addAll(convertToAggregationToStore(twoFortyMinuteAggregations));
            aggregationToStoreList.addAll(convertToAggregationToStore(fourEightyMinuteAggregations));
            aggregationToStoreList.addAll(convertToAggregationToStore(oneFourtyFourtyMinuteAggregations));
        }

        aggregationsRepository.saveAll(aggregationToStoreList);
    }

    private Aggregation generateRandomAggregation() {
        double min = 10.0 + (50.0 - 10.0) * random.nextDouble();
        double max = min + (50.0 - min) * random.nextDouble();
        long count = 1 + random.nextInt(10);
        double sum = min * count + (max - min) * random.nextDouble() * count;
        double average = sum / count;

        return new Aggregation(min, max, sum, average, count);
    }

    private List<AggregatedDetection> generateDetections(Room room, Date startDate, Date endDate,
            int intervalInMinutes) {
        List<AggregatedDetection> detections = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate)) {
            AggregatedDetection detection = new AggregatedDetection(
                    intervalInMinutes,
                    0,
                    room.getName(),
                    generateRandomAggregation());
            detection.setId(UUID.randomUUID().toString());
            detection.setParentId(null); // No parent for the base interval
            detection.setTimeStamp(calendar.getTime());
            detections.add(detection);

            calendar.add(Calendar.MINUTE, intervalInMinutes);
        }

        return detections;
    }

    private List<AggregatedDetection> createAggregations(List<AggregatedDetection> baseDetections,
            int targetIntervalInMinutes) {
        List<AggregatedDetection> aggregations = new ArrayList<>();
        int baseInterval = baseDetections.get(0).getInterval(); // Get the base interval from the detections
        int factor = targetIntervalInMinutes / baseInterval;

        for (int i = 0; i < baseDetections.size(); i += factor) {
            if (i + factor <= baseDetections.size()) {
                double min = Double.MAX_VALUE;
                double max = Double.MIN_VALUE;
                double sum = 0;
                long count = 0;

                for (int j = i; j < i + factor; j++) {
                    Aggregation baseAggregation = baseDetections.get(j).getAggregation();
                    if (baseAggregation.getMin() < min) {
                        min = baseAggregation.getMin();
                    }
                    if (baseAggregation.getMax() > max) {
                        max = baseAggregation.getMax();
                    }
                    sum += baseAggregation.getSum();
                    count += baseAggregation.getCount();
                }

                double average = sum / count;

                Aggregation aggregatedAggregation = new Aggregation(min, max, sum, average, count);

                AggregatedDetection aggregatedDetection = new AggregatedDetection();
                aggregatedDetection.setId(UUID.randomUUID().toString());
                aggregatedDetection.setParentId(baseDetections.get(i).getId());
                aggregatedDetection.setInterval(targetIntervalInMinutes);
                aggregatedDetection.setPreviousInterval(baseInterval);
                aggregatedDetection.setLocation(baseDetections.get(i).getLocation());
                aggregatedDetection.setTimeStamp(baseDetections.get(i).getTimeStamp());
                aggregatedDetection.setAggregation(aggregatedAggregation);

                aggregations.add(aggregatedDetection);
            }
        }

        return aggregations;
    }

    private List<AggregationToStore> convertToAggregationToStore(List<AggregatedDetection> detections) {
        return detections.stream()
                .map(AggregationToStore::new)
                .collect(Collectors.toList());
    }

}
