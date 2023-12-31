package org.hca.middleware.EventHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventHandlerApplication {

	private static final Logger LOGGER = LogManager.getLogger(EventHandlerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EventHandlerApplication.class, args);
	}

}
