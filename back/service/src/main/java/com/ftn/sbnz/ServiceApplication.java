package com.ftn.sbnz;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ftn.sbnz.service.LoadTestDataService;

@SpringBootApplication()
// public class ServiceApplication implements CommandLineRunner {
public class ServiceApplication {

	@Autowired
	private LoadTestDataService loadTestDataService;

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	// @Override
	// public void run(String... args) throws Exception {
	// // loadTestDataService.createRoomHierarchy();
	// // loadTestDataService.createAggregationsForBottomLevelRooms();
	// }

	@Bean
	public KieContainer kieContainer() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("com.ftn.sbnz", "kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);

		return kContainer;
	}

}