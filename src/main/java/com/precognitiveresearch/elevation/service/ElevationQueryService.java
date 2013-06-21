package com.precognitiveresearch.elevation.service;

import com.precognitiveresearch.elevation.domain.Coordinate;
import com.precognitiveresearch.elevation.domain.Elevation;
import com.precognitiveresearch.elevation.exception.ElevationNotFoundException;

public interface ElevationQueryService {

	public Elevation getElevation(Coordinate coordinate) throws ElevationNotFoundException;
	
}
