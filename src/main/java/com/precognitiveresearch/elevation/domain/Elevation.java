package com.precognitiveresearch.elevation.domain;

import java.io.Serializable;

/**
 * Represents elevation of a specific coordinate on Earth, in meters.
 * 
 * @author Andy Sinesio
 *
 * @Immutable
 */
public final class Elevation implements Comparable<Elevation> {
	
	public static final Elevation UNKNOWN = new Elevation(Integer.MIN_VALUE);
	
	private final int elevation;
	private final String units;
	
	public static Elevation newMetricElevation(int elevation) {
		return new Elevation(elevation);
	}

	private Elevation(int elevation) {
		super();
		this.elevation = elevation;
		this.units = "meters";
	}

	public int getElevation() {
		return elevation;
	}
	
	public String getUnits() {
		return units;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + elevation;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Elevation other = (Elevation) obj;
		if (elevation != other.elevation)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Elevation [elevation=" + elevation + "]";
	}

	@Override
	public int compareTo(Elevation other) {
		return Integer.valueOf(elevation).compareTo(Integer.valueOf(other.getElevation()));
	}	
	
}
