package com.precognitiveresearch.elevation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.precognitiveresearch.elevation.domain.Coordinate;
import com.precognitiveresearch.elevation.domain.Elevation;
import com.precognitiveresearch.elevation.domain.ElevationSegment;

@Service
public class ElevationQueryServiceImpl implements ElevationQueryService {

	private final CacheManager cacheManager;
	private final ElevationDataLoader elevationDataLoader;
	
	@Autowired	
	public ElevationQueryServiceImpl(ElevationDataLoader elevationDataLoader, CacheManager cacheManager) {
		super();
		this.elevationDataLoader = elevationDataLoader;
		this.cacheManager = cacheManager;
	}

	@Override
	@Cacheable("elevationQueries")
	public Elevation getElevation(Coordinate coordinate) {
		//cacheManager.getCache("elevationSegments");
		ElevationSegment segment = elevationDataLoader.load(coordinate.getSegmentIdentifier());
		return segment.getElevationForCoordinate(coordinate);
	}

}
