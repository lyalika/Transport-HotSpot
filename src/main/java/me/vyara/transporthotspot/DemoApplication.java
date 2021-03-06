package me.vyara.transporthotspot;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.vyara.transporthotspot.api.InterfaceRMI;
import me.vyara.transporthotspot.api.ServerRMI;
import me.vyara.transporthotspot.entities.*;

@SpringBootApplication
public class DemoApplication {
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner rmiApiServer(LineRepository lineRepository, StopRepository stopRepository, ArrivalRepository arrivalRepository) {
		return (args) -> {
			
			try {
	            ServerRMI obj = new ServerRMI(stopRepository, lineRepository, arrivalRepository);
	            InterfaceRMI stub = (InterfaceRMI) UnicastRemoteObject.exportObject(obj, 0);
	            
	            // Bind the remote object's stub in the registry
	            Registry registry = LocateRegistry.createRegistry(1099);
	            registry.bind("transport", stub);

	            System.err.print("Server ready");
	        } catch (Exception e) {
	            log.error("Server exception: " + e.toString());
	            e.printStackTrace();
	        }
		};
	}
}

