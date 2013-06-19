package com.precognitiveresearch.elevation;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.annotation.Autowired;

import com.precognitiveresearch.elevation.domain.Coordinate;
import com.precognitiveresearch.elevation.domain.Elevation;
import com.precognitiveresearch.elevation.service.ElevationQueryService;

public class ElevationResource extends ServerResource {

	@Autowired
	private ElevationQueryService elevationQueryService;

	@Get
	public Elevation getElevation() {
		String latitude = (String) getRequestAttributes().get("latitude");
		String longitude = (String) getRequestAttributes().get("longitude");

		Coordinate coordinate = new Coordinate(Double.valueOf(latitude),
				Double.valueOf(longitude));
		return elevationQueryService.getElevation(coordinate);
	}

	public ElevationQueryService getElevationQueryService() {
		return elevationQueryService;
	}

	public void setElevationQueryService(
			ElevationQueryService elevationQueryService) {
		this.elevationQueryService = elevationQueryService;
	}

}
