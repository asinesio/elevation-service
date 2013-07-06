package com.precognitiveresearch.elevation.domain

import org.springframework.util.SerializationUtils

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

	def "Verify get elevation for segment"() {
		setup:
		List<Short> elevations = Arrays.asList(new Short[1201*1201])
		elevations[arrayIndex] = (short) 1234
		Coordinate coordinate = Coordinate.newCoordinate(latitude, longitude)
		ElevationSegment segment = ElevationSegment.newSegmentFromCoordinate(coordinate, elevations)

		expect:
		segment.getElevationForCoordinate(coordinate).equals(Elevation.newMetricElevation(1234))
		
		where:
		latitude | longitude | arrayIndex
		37       | 100       | 1201 * 1200 // first column of last row of data, 1441200
		100.1    | 100.1     | 120 + 1201 * (1201 - 120 - 1) // 120 rows and columns in = 1297200
	}
	
	def "Ensure serialization works"() {
		setup:
		ElevationSegment original = ElevationSegment.newSegmentFromIdentifier("Testing 123", [1,2,3])
		
		when:
		byte[] data = SerializationUtils.serialize(original)
		ElevationSegment result = SerializationUtils.deserialize(data)
		
		then: "Verify data fields"
		result.identifier == original.identifier
		result.elevations == original.elevations
	}
	
	def "Ensure serialization proxy "() {
		setup:
		ElevationSegment original = ElevationSegment.newSegmentFromIdentifier("Testing 123", [1,2,3])
		
		when:
		byte[] data = SerializationUtils.serialize(original)
		ElevationSegment result = SerializationUtils.deserialize(data)
		result.getElevations().add(4) // should throw UnsupportedOperationException
		
		then: "Verify elevation data cannot be modified"
		thrown(UnsupportedOperationException)
	}

}
