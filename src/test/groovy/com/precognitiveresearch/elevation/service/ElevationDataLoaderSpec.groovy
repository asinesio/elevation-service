package com.precognitiveresearch.elevation.service

import spock.lang.Specification

import com.precognitiveresearch.elevation.domain.Elevation
import com.precognitiveresearch.elevation.domain.ElevationObjectMother
import com.precognitiveresearch.elevation.domain.ElevationSegment

class ElevationDataLoaderSpec extends Specification {

	def "Verify data can be loaded from USGS's web servers"() {
		setup:
		Properties properties = new Properties() ;
		properties.load(this.getClass().getResourceAsStream("/application.properties"));
		ElevationDataLoader loader = new ElevationDataLoaderImpl(properties.get("srtm.base.url"),properties.get("srtm.file.extension"))
		
		when:
		ElevationSegment segment = loader.load("N37W123")
		
		then:
		segment != null
		segment.identifier == "N37W123"
		segment.elevations.size() == 1201 * 1201
		segment.elevations[0] == (short) 97 
		segment.getElevationForCoordinate(ElevationObjectMother.APPLE_HQ) == new Elevation(69)  // based on data from GeoNames' implementation of SRTM3
	}
}
