package com.precognitiveresearch.elevation.service;

import com.precognitiveresearch.elevation.domain.ElevationSegment;
import com.precognitiveresearch.elevation.exception.ElevationNotFoundException;

public interface ElevationDataLoader {

	public ElevationSegment load(String segmentIdentifier) throws ElevationNotFoundException;

}
