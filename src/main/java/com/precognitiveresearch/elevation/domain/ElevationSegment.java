package com.precognitiveresearch.elevation.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an "elevation segment" which correlates to one SRTM file. These
 * objects will be cached.
 * 
 * @author Andy Sinesio
 * 
 * @Immutable
 */
public class ElevationSegment implements Serializable {

	private static final Logger LOG = LoggerFactory.getLogger(ElevationSegment.class);
	
	private static final long serialVersionUID = 3628951214741897043L;

	private static final int SEGMENT_WIDTH = 1200;
	private static final int SEGMENT_HEIGHT = 1200;
	private static final int SRTM_COL_SIZE = 1201;

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
		
		return index > 0 && index < elevations.size() ? new Elevation(elevations.get(index))
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
	
	@Override
	public String toString() {
		return "ElevationSegment [identifier=" + identifier + ", elevation count="
				+ elevations.size() + "]";
	}

	/**
	 * Calculate the offset into the array to find the data where coordinate is located.
	 * 
	 * The basic algorithm is based upon:
	 * 	1) Each segment is 1 degree by 1 degree.
	 *  2) The segment identifier is the bottom left corner, e.g. N37W123 for (37.1,-122.1).
	 *  3) Finding the row and column is done by multiplying the fractional part of the coordinate by the dimensions of the data.
	 *  4) The "row" from 3 is actually counted from the bottom, but the data is top-down, so we need to invert it.
	 * 
	 * @param coordinate
	 * @return the index in the elevations list for the coordinate.
	 */
	private int getElevationIndexForCoordinate(Coordinate coordinate) {
		int row = (int) Math.round(SEGMENT_HEIGHT
				* (coordinate.getLatitude() - Math.floor(coordinate
						.getLatitude())));
		int col = (int) Math.round(SEGMENT_WIDTH
				* (coordinate.getLongitude() - Math.floor(coordinate
						.getLongitude())));
		// col is correct, but we need to offset by # of rows (and rows count from top to bottom,
		int index = col + SRTM_COL_SIZE * (SRTM_COL_SIZE - row - 1);
		LOG.info(coordinate.toString() + " elevation index: row = " + row + ", col = " + col + " index = " + index);
		
		return index;
	}

}
