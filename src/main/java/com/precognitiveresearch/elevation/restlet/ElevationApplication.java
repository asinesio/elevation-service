package com.precognitiveresearch.elevation.restlet;

import org.restlet.Component;
import org.restlet.ext.spring.SpringComponent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * A class that bootstraps a Restlet web container (by default, it listens on port 3000.)
 * @author Andy Sinesio
 *
 */
public class ElevationApplication {

	/**
	 * Bootstrap a Restlet web container.
	 * @param args	Command line arguments (ignored)
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Component component = applicationContext.getBean(SpringComponent.class);
		component.start();
		applicationContext.close();
	}

}
