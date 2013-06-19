package com.precognitiveresearch.elevation.domain

import spock.lang.Specification

class CoordinateSpec extends Specification {
	
	def "Verify coordinate fields"() {
		setup:
		double latitude = 37.12345d
		double longitude = -122.456d
		Coordinate coordinate = new Coordinate(latitude, longitude)
		
		expect:
		coordinate.latitude == latitude
		coordinate.longitude == longitude
	}
	
	def "Check the segment identifier is accurate"() {
		expect:
		ElevationObjectMother.APPLE_HQ.getSegmentIdentifier() == "N37W123"
	}
	
	def "Verify equals"() {
		setup:
		Coordinate coords = new Coordinate(ElevationObjectMother.APPLE_HQ.latitude, ElevationObjectMother.APPLE_HQ.longitude);
		
		expect:
		coords == ElevationObjectMother.APPLE_HQ
	}
}
