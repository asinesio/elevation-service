package com.precognitiveresearch.elevation;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public class ElevationApplication extends Application {

	public static void main(String[] args) throws Exception {
		// Create a component

		Component component = new Component();
		component.getServers().add(Protocol.HTTP, 8111);

		// Create an application
		Application application = new ElevationApplication();

		// Attach the application to the component and start it
		component.getDefaultHost().attachDefault(application);
		component.start();
	}

	@Override
	public Restlet createInboundRoot() {
		// Create a router
		Router router = new Router(getContext());

		// Attach the resources to the router
		router.attach("/elevation/{latitude}/{longitude}",
				ElevationResource.class);

		// Return the root router
		return router;
	}
}
