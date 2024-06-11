// package com.ftn.sbnz.service.tests;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.Date;
// import java.util.concurrent.TimeUnit;
// import java.util.stream.Collectors;

// import org.drools.core.ClassObjectFilter;
// import org.drools.core.time.SessionPseudoClock;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.kie.api.runtime.KieContainer;
// import org.kie.api.runtime.KieSession;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.ftn.sbnz.model.events.PersonDetectionEvent;
// import com.ftn.sbnz.model.models.AggregatePeople;
// import com.ftn.sbnz.model.models.AggregatedDetection;
// import com.ftn.sbnz.model.models.Aggregation;

// @SpringBootTest
// class CEPTest {

// private final String LOCATION = "Mlecni proizvodi";

// private KieSession session;

// @Autowired
// private KieContainer kieContainer;

// @BeforeEach
// public void init() {
// session = kieContainer.newKieSession("cepKsessionTest");
// }

// private void advanceTime(int forTime) {
// SessionPseudoClock clock = session.getSessionClock();
// clock.advanceTime(forTime, TimeUnit.SECONDS);
// }

// private Date getCurrentPseudoTime() {
// SessionPseudoClock clock = session.getSessionClock();
// return new Date(clock.getCurrentTime());
// }

// private void insertValueReceived(int value) {
// PersonDetectionEvent vr = new PersonDetectionEvent(LOCATION, value);
// session.insert(vr);
// }

// private void insertValue5minAggregated(Double min, Double max, Double sum,
// Double average, Long count) {
// var va5 = new AggregatedDetection(5, 0, LOCATION, new Aggregation(min, max,
// sum, average, count));
// session.insert(va5);
// }

// private void insertValue15minAggregated(Double min, Double max, Double sum,
// Double average, Long count) {
// var va0015 = new AggregatedDetection(15, 5, LOCATION, new Aggregation(min,
// max, sum, average, count));
// va0015.setTimeStamp(getCurrentPseudoTime());
// session.insert(va0015);
// }

// private void insertValue30minAggregated(Double min, Double max, Double sum,
// Double average, Long count) {
// var va0030 = new AggregatedDetection(30, 15, LOCATION, new Aggregation(min,
// max, sum, average, count));
// va0030.setTimeStamp(getCurrentPseudoTime());
// session.insert(va0030);
// }

// private void insertValue1hAggregated(Double min, Double max, Double sum,
// Double average, Long count) {
// var va0060 = new AggregatedDetection(60, 30, LOCATION, new Aggregation(min,
// max, sum, average, count));
// va0060.setTimeStamp(getCurrentPseudoTime());
// session.insert(va0060);
// }

// private void insertValue2hAggregated(Double min, Double max, Double sum,
// Double average, Long count) {
// var va0240 = new AggregatedDetection(120, 1, LOCATION, new Aggregation(min,
// max, sum, average, count));
// va0240.setTimeStamp(getCurrentPseudoTime());
// session.insert(va0240);
// }

// private void insertValue4hAggregated(Double min, Double max, Double sum,
// Double average, Long count) {
// var va0480 = new AggregatedDetection(240, 2, LOCATION, new Aggregation(min,
// max, sum, average, count));
// va0480.setTimeStamp(getCurrentPseudoTime());
// session.insert(va0480);
// }

// private void insertValue8hAggregated(Double min, Double max, Double sum,
// Double average, Long count) {
// var va0480 = new AggregatedDetection(480, 4, LOCATION, new Aggregation(min,
// max, sum, average, count));
// va0480.setTimeStamp(getCurrentPseudoTime());
// session.insert(va0480);
// }

// private void insertValue24hAggregated(Double min, Double max, Double sum,
// Double average, Long count) {
// var va0480 = new AggregatedDetection(1440, 8, LOCATION, new Aggregation(min,
// max, sum, average, count));
// va0480.setTimeStamp(getCurrentPseudoTime());
// session.insert(va0480);
// }

// private void insertAggregateParam(int interval) {
// session.insert(new AggregatePeople(interval, LOCATION));
// }

// private void fire() {
// session.fireAllRules();
// }

// private <T> ArrayList<T> getFactsFromKieSession(Class<T> classType) {
// return new ArrayList<>((Collection<T>) session.getObjects(new
// ClassObjectFilter(classType)));
// }

// private void assertValueAggregated(AggregatedDetection valueAggregated,
// Double min, Double max, Double sum,
// Double average, Long count) {
// assertEquals(min, valueAggregated.getAggregation().getMin());
// assertEquals(max, valueAggregated.getAggregation().getMax());
// assertEquals(sum, valueAggregated.getAggregation().getSum());
// assertEquals(average, valueAggregated.getAggregation().getAverage());
// assertEquals(count, valueAggregated.getAggregation().getCount());
// }

// private void assertParentIsSet(Class<?> childrenClass, Class<?> parentClass)
// {
// var children = getFactsFromKieSession(childrenClass);
// var parent = getFactsFromKieSession(parentClass).get(0);

// for (var child : children) {
// assertEquals(((AggregatedDetection) parent).getId(), ((AggregatedDetection)
// child).getParentId());
// }
// }

// @Test
// public void Test_Aggregate_0005() {

// advanceTime(1);
// insertValueReceived(12);

// advanceTime(1);
// insertValueReceived(22);

// advanceTime(2);
// insertValueReceived(17);

// insertAggregateParam(5);
// fire();

// var aggregateParams = getFactsFromKieSession(AggregatePeople.class);
// assertEquals(0, aggregateParams.size());

// var aggregate0005 = getFactsFromKieSession(AggregatedDetection.class);
// assertEquals(1, aggregate0005.size());

// assertValueAggregated(aggregate0005.get(0), 12D, 22D, 51D, 17D, 3L);
// }

// @Test
// public void Test_Aggregate_0015() {

// insertValue5minAggregated(10D, 22D, 44D, 11D, 4L);
// advanceTime(5);

// insertValue5minAggregated(2D, 33D, 100D, 20D, 5L);
// advanceTime(5);

// insertValue5minAggregated(1D, 1D, 1D, 1D, 1L);
// fire();

// var aggregateParams = getFactsFromKieSession(AggregatePeople.class);
// assertEquals(0, aggregateParams.size());

// var aggregate0015 =
// getFactsFromKieSession(AggregatedDetection.class).stream().filter(a ->
// a.interval == 15)
// .collect(Collectors.toList());
// assertEquals(1, aggregate0015.size());

// assertValueAggregated(aggregate0015.get(0), 1D, 33D, 145D, 14.5D, 10L);
// }

// @Test
// public void Test_Aggregate_0030() {

// insertValue15minAggregated(3D, 13D, 24D, 7D, 3L);
// advanceTime(15);

// insertValue15minAggregated(6D, 11D, 25D, 6.25D, 4L);
// fire();

// var aggregateParams =
// getFactsFromKieSession(AggregatePeople.class).stream().filter(a -> a.interval
// == 30)
// .collect(Collectors.toList());
// assertEquals(0, aggregateParams.size());

// var aggregate0030 =
// getFactsFromKieSession(AggregatedDetection.class).stream().filter(a ->
// a.interval == 30)
// .collect(Collectors.toList());
// assertEquals(1, aggregate0030.size());

// assertValueAggregated(aggregate0030.get(0), 3D, 13D, 49D, 7D, 7L);
// }

// @Test
// public void Test_Aggregate_0060() {

// insertValue30minAggregated(7D, 56D, 96D, 16D, 6L);
// advanceTime(30);

// insertValue30minAggregated(11D, 13D, 24D, 12D, 2L);
// fire();

// var aggregateParams =
// getFactsFromKieSession(AggregatePeople.class).stream().filter(a -> a.interval
// == 60)
// .collect(Collectors.toList());
// assertEquals(0, aggregateParams.size());

// var aggregate0060 =
// getFactsFromKieSession(AggregatedDetection.class).stream().filter(a ->
// a.interval == 60)
// .collect(Collectors.toList());
// assertEquals(1, aggregate0060.size());

// assertValueAggregated(aggregate0060.get(0), 7D, 56D, 120D, 15D, 8L);

// }

// @Test
// public void Test_Aggregate_0240() {

// insertValue2hAggregated(1D, 14D, 22D, 6D, 4L);
// advanceTime(120);

// insertValue2hAggregated(3D, 22D, 30D, 10D, 3L);
// fire();

// var aggregateParams =
// getFactsFromKieSession(AggregatePeople.class).stream().filter(a -> a.interval
// == 240)
// .collect(Collectors.toList());
// assertEquals(0, aggregateParams.size());

// var aggregate0240 =
// getFactsFromKieSession(AggregatedDetection.class).stream().filter(a ->
// a.interval == 240)
// .collect(Collectors.toList());
// assertEquals(1, aggregate0240.size());

// // assertValueAggregated(aggregate0240.get(0), 1D, 22D, 52D, 16D / 2L,
// // 12L);

// }

// @Test
// public void Test_Aggregate_0480() {

// insertValue4hAggregated(1D, 6D, 10D, 2.5D, 4L);
// advanceTime(240);

// insertValue4hAggregated(2D, 18D, 20D, 10D, 2L);
// fire();

// var aggregateParams =
// getFactsFromKieSession(AggregatePeople.class).stream().filter(a -> a.interval
// == 480)
// .collect(Collectors.toList());
// assertEquals(0, aggregateParams.size());

// var aggregate0480 =
// getFactsFromKieSession(AggregatedDetection.class).stream().filter(a ->
// a.interval == 480)
// .collect(Collectors.toList());
// assertEquals(1, aggregate0480.size());

// assertValueAggregated(aggregate0480.get(0), 1D, 18D, 30D, 5D, 6L);

// }

// @Test
// public void Test_Aggregate_1440() {

// insertValue8hAggregated(7D, 12D, 30D, 10D, 3L);
// advanceTime(480);

// insertValue8hAggregated(12D, 30D, 42D, 21D, 2L);
// advanceTime(480);

// insertValue8hAggregated(8D, 16D, 38D, 7.6D, 5L);
// fire();

// var aggregateParams =
// getFactsFromKieSession(AggregatePeople.class).stream().filter(a -> a.interval
// == 1440)
// .collect(Collectors.toList());
// assertEquals(0, aggregateParams.size());

// var aggregate1440 =
// getFactsFromKieSession(AggregatedDetection.class).stream().filter(a ->
// a.interval == 1440)
// .collect(Collectors.toList());
// assertEquals(1, aggregate1440.size());

// assertValueAggregated(aggregate1440.get(0), 7D, 30D, 110D, 11D, 10L);

// }
// }