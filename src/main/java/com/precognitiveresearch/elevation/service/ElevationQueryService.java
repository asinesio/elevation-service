package com.precognitiveresearch.elevation.service;

import com.precognitiveresearch.elevation.domain.Coordinate;
import com.precognitiveresearch.elevation.domain.Elevation;

public interface ElevationQueryService {

	public Elevation getElevation(Coordinate coordinate);
	
}
