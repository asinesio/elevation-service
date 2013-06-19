package com.precognitiveresearch.elevation.service;

import com.precognitiveresearch.elevation.domain.Coordinate;
import com.precognitiveresearch.elevation.domain.ElevationSegment;

public interface ElevationDataLoader {

	public ElevationSegment load(Coordinate coordinate);
	
}
