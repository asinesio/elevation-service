package com.precognitiveresearch.elevation.domain

import spock.lang.Specification

class ElevationSegmentSpec extends Specification {

	def "Verify construction"() {
		setup:
		String segmentID = "segmentID"
		List<Short> elevations = [1..10]
		ElevationSegment segment = new ElevationSegment("segmentID", elevations)
		
		expect:
		segment.identifier == segmentID
		segment.elevations == elevations
	}
	
	def "Verify appliesToCoordinate() matches segments"() {
		setup:
		ElevationSegment segment = new ElevationSegment(ElevationObjectMother.APPLE_HQ, [1])
		
		expect:
		segment.appliesToCoordinate(ElevationObjectMother.APPLE_HQ)
	}
	
}
