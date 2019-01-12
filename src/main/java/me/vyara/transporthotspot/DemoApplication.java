package me.vyara.transporthotspot;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.vyara.transporthotspot.entities.Line;
import me.vyara.transporthotspot.entities.LineRepository;

@SpringBootApplication
public class DemoApplication {
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(LineRepository repository) {
		return (args) -> {
			log.info("Lines found with findAll():");
			log.info("-------------------------------");
			for (Line customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");
		};
	}
}

