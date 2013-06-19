package com.precognitiveresearch.elevation.service;

import java.net.URL;

import com.precognitiveresearch.elevation.domain.Coordinate;
import com.precognitiveresearch.elevation.domain.ElevationSegment;

public class ElevationDataLoaderImpl implements ElevationDataLoader {
	
	private final URL baseFileURL;
	private final String fileExtension;

	public ElevationDataLoaderImpl(URL baseFileURL, String fileExtension) {
		super();
		this.baseFileURL = baseFileURL;
		this.fileExtension = fileExtension;
	}

	@Override
	public ElevationSegment load(Coordinate coordinate) {
		// load a segment for a coordinate
		return null;
	}
	

}
