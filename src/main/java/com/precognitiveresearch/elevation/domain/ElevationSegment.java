package com.precognitiveresearch.elevation.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Represents an "elevation segment" which correlates to one SRTM file. 
 * These objects will be cached.
 * 
 * @author Andy Sinesio
 *
 * @Immutable
 */
public class ElevationSegment implements Serializable {

	private static final long serialVersionUID = 3628951214741897043L;
	
	private final String identifier;
	private final List<Short> elevations;

	public ElevationSegment(Coordinate coordinate, List<Short> elevations) {
		this(coordinate.getSegmentIdentifier(), elevations);
	}

	public ElevationSegment(String identifier, List<Short> elevations) {
		if (identifier == null || elevations == null) {
			throw new IllegalArgumentException("identifier and elevations are both required to be non null");
		}
		this.identifier = identifier;
		this.elevations = Collections.unmodifiableList(elevations);
	}
		
	public Elevation getElevationForCoordinate(Coordinate coordinate) {
		// TODO calculate the array value for the decimal
		return new Elevation(elevations.get(0));
	}
	
	public boolean appliesToCoordinate(Coordinate coordinate) {
		return coordinate.getSegmentIdentifier().equals(identifier);
	}

	public String getIdentifier() {
		return identifier;
	}

	public List<Short> getElevations() {
		return elevations;
	}
	
}
