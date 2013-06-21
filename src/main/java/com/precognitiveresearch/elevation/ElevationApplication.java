package com.precognitiveresearch.elevation;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.Component;
import org.restlet.ext.spring.SpringComponent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ElevationApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Logger logger = Logger.getAnonymousLogger();
        // LOG this level to the log
        logger.setLevel(Level.FINER);
		Component component = applicationContext.getBean(SpringComponent.class);
		
		component.start();
	}

}
