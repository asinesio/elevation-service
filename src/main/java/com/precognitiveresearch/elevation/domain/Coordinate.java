package com.precognitiveresearch.elevation.domain;

import java.io.Serializable;

public class Coordinate implements Serializable {

	private static final long serialVersionUID = 1551094756045774365L;

	private final double latitude;
	private final double longitude;

	public Coordinate(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getSegmentIdentifier() {
		String left = String.format("%03d", (int) Math.abs(Math.floor(longitude)));
		String bottom = String.format("%02d", (int) Math.abs(Math.floor(latitude)));
		return ((latitude > 0) ? "N" : "S") + bottom + ((longitude > 0) ? "E" : "W") + left;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Coordinate other = (Coordinate) obj;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coordinate [latitude=" + latitude + ", longitude=" + longitude
				+ "]";
	}
}
