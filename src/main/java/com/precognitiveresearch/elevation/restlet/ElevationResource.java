package com.precognitiveresearch.elevation.restlet;

import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.precognitiveresearch.elevation.domain.Coordinate;
import com.precognitiveresearch.elevation.domain.Elevation;
import com.precognitiveresearch.elevation.exception.ElevationNotFoundException;
import com.precognitiveresearch.elevation.service.ElevationQueryService;

/**
 * A Restlet Server Resource that returns the elevation of a given latitude/longitude in meters.
 * 
 * @author Andy Sinesio
 */
public class ElevationResource extends ServerResource {
	private static final Logger LOG = LoggerFactory
			.getLogger(ElevationResource.class);

	private final ElevationQueryService elevationQueryService;

	@Autowired
	public ElevationResource(ElevationQueryService elevationQueryService) {
		super();
		this.elevationQueryService = elevationQueryService;
	}

	/**
	 * Get the elevation of a given coordinate, in meters.
	 * 
	 * Parameters:
	 * latitude		The latitude, in decimal degrees. ex: 37.2345
	 * longitude	The longitude, in decimal degrees. ex: -122.323
	 *
	 * @return	Representation that can be serialized to JSON by Restlet. (Elevation JSON, e.g. { elevation: 100, units: "meters" })
	 */
	@Get("json")
	public Representation getElevation() {
		String latitude = (String) getRequestAttributes().get("latitude");
		String longitude = (String) getRequestAttributes().get("longitude");
		
		if (StringUtils.isEmpty(latitude) || StringUtils.isEmpty(longitude)) {
			this.setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return new JacksonRepresentation<String>("Latitude and longitude are both required parameters.");
		}
		
		try {
			Coordinate coordinate = Coordinate.newCoordinate(Double.valueOf(latitude),
					Double.valueOf(longitude));
			LOG.debug("Querying for coordinate: " + coordinate.toString());
			Elevation result = elevationQueryService.getElevation(coordinate);
			LOG.debug("Elevation result was: " + result.toString());
			return new JacksonRepresentation<Elevation>(result);
		} catch (NumberFormatException e) {
			this.setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return new JacksonRepresentation<String>("Latitude and longitude must both be numeric parameters.");
		} catch (ElevationNotFoundException e) {
			LOG.error("Elevation not found", e);
			this.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			return new JacksonRepresentation<String>(e.getMessage());
		}
	}

	public ElevationQueryService getElevationQueryService() {
		return elevationQueryService;
	}
}
