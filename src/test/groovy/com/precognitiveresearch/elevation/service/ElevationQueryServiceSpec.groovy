package com.precognitiveresearch.elevation.service

import spock.lang.Specification

import com.precognitiveresearch.elevation.domain.Coordinate
import com.precognitiveresearch.elevation.domain.Elevation
import com.precognitiveresearch.elevation.domain.ElevationSegment


class ElevationQueryServiceSpec extends Specification {

	def "Verify query service calls dependencies"() {
		setup:
		def mockLoader = Mock(ElevationDataLoader)
		def queryService = new ElevationQueryServiceImpl(mockLoader)
		def coordinate = new Coordinate(1,1)
		List<Short> elevations = new ArrayList<Short>()
		elevations.add((short) 10)
		
		when:
		def elevation = queryService.getElevation(coordinate)
		
		then:
		1 * mockLoader.load(coordinate.getSegmentIdentifier()) >> new ElevationSegment(coordinate, elevations)
		elevation == Elevation.UNKNOWN
	}
}
