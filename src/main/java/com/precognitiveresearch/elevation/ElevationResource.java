package com.precognitiveresearch.elevation;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

//@Path("/") // this is the root resource  
//@Produces("application/json")  
//@Component
public class ElevationResource extends ServerResource {

	private String latitude;
	private String longitude;
	
	public void init() {
		 this.latitude = (String) getRequestAttributes().get("latitude");
		 this.longitude = (String) getRequestAttributes().get("longitude");
	}
	
	 @Get
	 public int getElevation() {
		 return 0;
	 }

}
