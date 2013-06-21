package com.precognitiveresearch.elevation.domain

import spock.lang.Specification

class ElevationSpec extends Specification {

	def "Verify construction of Elevation object."() {
		expect:
		new Elevation(100).elevation == 100
	}
	
	def "Verify equals() of elevation object"() {
		expect:
		new Elevation(-99) == new Elevation(-99)
	}
}
