package com.precognitiveresearch.elevation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.precognitiveresearch.elevation.domain.Coordinate;
import com.precognitiveresearch.elevation.domain.Elevation;
import com.precognitiveresearch.elevation.domain.ElevationSegment;
import com.precognitiveresearch.elevation.exception.ElevationNotFoundException;

@Service
public class ElevationQueryServiceImpl implements ElevationQueryService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ElevationQueryServiceImpl.class);

	private final ElevationDataLoader elevationDataLoader;
	
	@Autowired	
	public ElevationQueryServiceImpl(ElevationDataLoader elevationDataLoader) {
		super();
		this.elevationDataLoader = elevationDataLoader;
	}

	@Override
	@Cacheable("elevationQueries")
	public Elevation getElevation(Coordinate coordinate) throws ElevationNotFoundException {
		LOG.info("Getting elevation segment for coordinate: " + coordinate.toString());
		ElevationSegment segment = elevationDataLoader.load(coordinate.getSegmentIdentifier());
		if (segment == null) {
			throw new ElevationNotFoundException("No elevation found for " + coordinate.toString());
		}
		LOG.info("Querying segment " + segment.toString() + " for elevation");
		return segment.getElevationForCoordinate(coordinate);
	}

}
