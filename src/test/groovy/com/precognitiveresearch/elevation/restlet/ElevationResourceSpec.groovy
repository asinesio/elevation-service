package com.precognitiveresearch.elevation.restlet

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

import org.restlet.Request
import org.restlet.representation.Representation

import spock.lang.Specification

import com.precognitiveresearch.elevation.domain.Coordinate
import com.precognitiveresearch.elevation.domain.Elevation
import com.precognitiveresearch.elevation.service.ElevationQueryService

class ElevationResourceSpec extends Specification {

	def "Verify get() for valid cases"() {
		setup:
		ElevationQueryService service = Mock(ElevationQueryService)
		ElevationResource resource = new ElevationResource(service)
		Request mockRequest = Mock(Request)
		resource.setRequest(mockRequest)
		ConcurrentMap attributes = new ConcurrentHashMap(["latitude": latitude, "longitude": longitude]);
		
		when:
		Representation representation = resource.getElevation()
		
		then:
		2 * mockRequest.getAttributes() >> attributes
		1 * service.getElevation(new Coordinate(Double.valueOf(latitude), Double.valueOf(longitude))) >> new Elevation(elevation)
		representation.getText() == """{"elevation":${elevation},"units":"meters"}""".toString()	
		
		where:
		latitude	|	longitude	| 	elevation
		"43"		|	"-13"		|	199
		"-42.31"	|	"134.54"	|	-123
		"0"			|	"0"			|	0
	}

}
