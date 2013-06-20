package com.precognitiveresearch.elevation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.precognitiveresearch.elevation.domain.Coordinate;
import com.precognitiveresearch.elevation.domain.Elevation;
import com.precognitiveresearch.elevation.domain.ElevationSegment;

@Service
public class ElevationQueryServiceImpl implements ElevationQueryService {

	private final ElevationDataLoader elevationDataLoader;
	
	@Autowired	
	public ElevationQueryServiceImpl(ElevationDataLoader elevationDataLoader) {
		super();
		this.elevationDataLoader = elevationDataLoader;
	}

	@Override
	public Elevation getElevation(Coordinate coordinate) {
		ElevationSegment segment = elevationDataLoader.load(coordinate);
		return segment.getElevationForCoordinate(coordinate);
	}

}
