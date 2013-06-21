package com.precognitiveresearch.elevation;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.precognitiveresearch.elevation.domain.Coordinate;
import com.precognitiveresearch.elevation.domain.Elevation;
import com.precognitiveresearch.elevation.exception.ElevationNotFoundException;
import com.precognitiveresearch.elevation.service.ElevationQueryService;

public class ElevationResource extends ServerResource {
	private static final Logger LOG = LoggerFactory.getLogger(ElevationResource.class);

	@Autowired
	private ElevationQueryService elevationQueryService;

	@Get
	public Elevation getElevation() {
		String latitude = (String) getRequestAttributes().get("latitude");
		String longitude = (String) getRequestAttributes().get("longitude");
		
		Coordinate coordinate = new Coordinate(Double.valueOf(latitude),
				Double.valueOf(longitude));
		LOG.info("Querying for coordinate: " + coordinate.toString());
		try {
			Elevation result = elevationQueryService.getElevation(coordinate);
			LOG.info("Elevation result was: " + result.toString());
			return result;
		} catch (ElevationNotFoundException e) {
			LOG.error("Elevation not found for coordinate " + coordinate.toString());
		}
		return null;
		
	}

	public ElevationQueryService getElevationQueryService() {
		return elevationQueryService;
	}

	public void setElevationQueryService(
			ElevationQueryService elevationQueryService) {
		this.elevationQueryService = elevationQueryService;
	}

}
