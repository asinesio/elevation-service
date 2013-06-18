package com.precognitiveresearch.elevation;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.precognitiveresearch.elevation.domain.Coordinate;
import com.precognitiveresearch.elevation.service.ElevationQueryService;


public class ElevationResource extends ServerResource {

	@Autowired
	private ElevationQueryService elevationQueryService;
	
	private Coordinate coordinate;
	
	public void init() {
		 String latitude = (String) getRequestAttributes().get("latitude");
		 String longitude = (String) getRequestAttributes().get("longitude");
	}
	
	 @Get
	 public int getElevation() {
		 return 0;
	 }

	public ElevationQueryService getElevationQueryService() {
		return elevationQueryService;
	}

	public void setElevationQueryService(ElevationQueryService elevationQueryService) {
		this.elevationQueryService = elevationQueryService;
	}
	 
	 

}
