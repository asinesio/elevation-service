package com.precognitiveresearch.elevation;

import org.restlet.Component;
import org.restlet.ext.spring.SpringComponent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ElevationApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		Component component = applicationContext.getBean(SpringComponent.class);
		
		component.start();
	}

}
