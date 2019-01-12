package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import entities.Line;
import entities.LineRepository;
import entities.Stop;
import entities.StopRepository;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("entities")
@EnableJpaRepositories("entities")
@ComponentScan("entities")
public class DemoApplication {
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner demo(LineRepository repository) {
//		return (args) -> {
//			repository.save(new Line(152, 3, "Tb", "4"));
//			repository.save(new Line(153, 3, "Tb", "5"));
//			repository.save(new Line(58, 1, "A", "N1"));
//			
//			for(Line line: repository.findAll()) {
//				log.info(line.toString());
//			}
//			Integer lineN = 58;
//			repository.findById(lineN)
//						.ifPresent(line -> 
//							log.info(line.toString()));
//						
//		};
//	}
	
	@Bean
	public CommandLineRunner demo2(StopRepository repository) {
		List<Line> list = new ArrayList<>();
		list.add(new Line(152, 3, "Tb", "4"));
		list.add(new Line(153, 3, "Tb", "5"));
		list.add(new Line(58, 1, "A", "N1"));
		return (args) -> {
			repository.save(new Stop(1866520,2599122.0491158157, 5263859.234852684, 73, "БУЛ. ПЕЙО ЯВОРОВ"));
			
			for(Stop stop: repository.findAll()) {
				log.info(stop.toString());
			}
			Long n = (long) 1866520;
			repository.findById(n).ifPresent(stop -> log.info(stop.toString()));
		};
	}
}

