package com.precognitiveresearch.elevation.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an "elevation segment" which correlates to one SRTM file. These
 * objects will be cached.
 * 
 * @author Andy Sinesio
 * 
 * @Immutable
 */
public class ElevationSegment implements Serializable {

	private static final long serialVersionUID = 3628951214741897043L;

	private static final int SEGMENT_WIDTH = 1200;
	private static final int SEGMENT_HEIGHT = 1200;

	private final String identifier;
	private final List<Short> elevations;

	public static final ElevationSegment NOT_FOUND = new ElevationSegment(
			"not found", new ArrayList<Short>());

	public ElevationSegment(Coordinate coordinate, List<Short> elevations) {
		this(coordinate.getSegmentIdentifier(), elevations);
	}

	public ElevationSegment(String identifier, List<Short> elevations) {
		if (identifier == null || elevations == null) {
			throw new IllegalArgumentException(
					"identifier and elevations are both required to be non null");
		}
		this.identifier = identifier;
		this.elevations = Collections.unmodifiableList(elevations);
	}

	public Elevation getElevationForCoordinate(Coordinate coordinate) {
		int index = getElevationIndexForCoordinate(coordinate);
		return index < elevations.size() ? new Elevation(elevations.get(index))
				: Elevation.UNKNOWN;
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

	private int getElevationIndexForCoordinate(Coordinate coordinate) {
		int row = (int) Math.round(SEGMENT_HEIGHT
				* (coordinate.getLatitude() - Math.floor(coordinate
						.getLatitude())));
		int col = (int) Math.round(SEGMENT_WIDTH
				* (coordinate.getLongitude() - Math.floor(coordinate
						.getLongitude())));
		return row * col;
	}

}
