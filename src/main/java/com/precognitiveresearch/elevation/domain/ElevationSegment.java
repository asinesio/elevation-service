package com.precognitiveresearch.elevation.domain;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.precognitiveresearch.elevation.exception.ElevationNotFoundException;

/**
 * Represents an "elevation segment" which correlates to one USGS SRTM file.
 * These objects will be cached in a DiskStore, so Serialization is critical.
 * 
 * A USGS SRTM3 file represents 1200 rows and columns of elevation data at 30
 * meter resolution.
 * 
 * @author Andy Sinesio
 * 
 * @Immutable
 */
public final class ElevationSegment implements Serializable {

	private static final long serialVersionUID = -8025170347813398241L;

	private static final Logger LOG = LoggerFactory
			.getLogger(ElevationSegment.class);

	private static final int SEGMENT_WIDTH = 1200;
	private static final int SEGMENT_HEIGHT = 1200;
	private static final int SRTM_COL_SIZE = 1201;

	private final String identifier;
	private final List<Short> elevations;

	/**
	 * Create an ElevationSegment that contains the specified coordinate and
	 * related elevation data.
	 * 
	 * @param coordinate
	 *            A Coordinate that is located somewhere within the data.
	 * @param elevations
	 *            A list of elevations, in meters, ordered from Northwest to
	 *            Southeast of the segment.
	 * @return An initialized instance.
	 */
	public static ElevationSegment newSegmentFromCoordinate(
			Coordinate coordinate, List<Short> elevations) {
		return new ElevationSegment(coordinate, elevations);
	}

	/**
	 * Create an ElevationSegment from a given segment identifier.
	 * 
	 * @param identifier
	 *            The segment identifier. As defined by the USGS SRTM data, this
	 *            is the filename of the .hgt file. It represents the southwest
	 *            corner of the data.
	 * @param elevations
	 *            A list of elevations, in meters, parsed from a USGS SRTM file.
	 * @return An initialized ElevationSegment.
	 */
	public static ElevationSegment newSegmentFromIdentifier(String identifier,
			List<Short> elevations) {
		return new ElevationSegment(identifier, elevations);
	}

	private ElevationSegment(Coordinate coordinate, List<Short> elevations) {
		this(coordinate.getSegmentIdentifier(), elevations);
	}

	private ElevationSegment(String identifier, List<Short> elevations) {
		if (identifier == null || elevations == null) {
			throw new IllegalArgumentException(
					"identifier and elevations are both required to be non null");
		}
		this.identifier = identifier;
		this.elevations = Collections.unmodifiableList(elevations);
	}

	public Elevation getElevationForCoordinate(Coordinate coordinate)
			throws ElevationNotFoundException {
		if (appliesToCoordinate(coordinate)) {
			int index = getElevationIndexForCoordinate(coordinate);
			if (index > 0 && index < elevations.size()) {
				return Elevation.newMetricElevation(elevations.get(index));
			}
		}
		throw new ElevationNotFoundException("Coordinate "
				+ coordinate.toString() + " is not found in segment "
				+ this.toString());
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
		return "ElevationSegment [identifier=" + identifier
				+ ", elevation count=" + elevations.size() + "]";
	}

	/**
	 * Calculate the offset into the array to find the data where coordinate is
	 * located.
	 * 
	 * The basic algorithm is based upon: 1) Each segment is 1 degree by 1
	 * degree. 2) The segment identifier is the bottom left corner, e.g. N37W123
	 * for (37.1,-122.1). 3) Finding the row and column is done by multiplying
	 * the fractional part of the coordinate by the dimensions of the data. 4)
	 * The "row" from 3 is actually counted from the bottom, but the data is
	 * top-down, so we need to invert it.
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
		// col is correct, but we need to offset by # of rows (and rows count
		// from top to bottom,
		int index = col + SRTM_COL_SIZE * (SRTM_COL_SIZE - row - 1);
		LOG.info(coordinate.toString() + " elevation index: row = " + row
				+ ", col = " + col + " index = " + index);

		return index;
	}

	private Object writeReplace() {
		return new SerializationProxy(this);
	}

	private void readObject(ObjectInputStream inputStream) throws InvalidObjectException {
		throw new InvalidObjectException("SerializationProxy required");
	}

	/**
	 * Handles all serialization duties for the ElevationSegment.
	 *
	 * This ensures that the ElevationSegment itself is never actually serialized and is
	 * always created using its constructor(s).
	 */
	private static class SerializationProxy implements Serializable {

		private static final long serialVersionUID = 5441579612845939560L;

		private final String identifier;
		private final List<Short> elevations;

		private SerializationProxy(ElevationSegment source) {
			this.identifier = source.getIdentifier();
			this.elevations = new ArrayList<Short>(source.getElevations()); // ArrayList is serializable
		}
		
		private Object readResolve() {
			return ElevationSegment.newSegmentFromIdentifier(identifier, elevations);
		}
	}

}
