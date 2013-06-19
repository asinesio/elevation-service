#Elevation Service

Elevation Service is a RESTFul web service that returns elevation data for any lat/long in North America.

##How it works

Elevation Service will download height files automatically from the USGS's file server, parse them, and return elevation data.

##Usage

Prerequisites: **Java 1.6**

* Clone the repository and change into it.

```
    git clone https://github.com/asinesio/elevation-service.git
    cd elevation-service
```

* Use gradle to run the app locally:

```
    ./gradlew run
```

* The app will begin running at http://localhost:3000.


## API

### elevation

Return elevation data for a given latitude/longitude. Example: `http://localhost:3000/elevation/37.331789/-122.029620`

| Method	| Response Content Types	| URL Format
| GET		| `application/json`		| `http://localhost:3000/elevation/{latitude}/{longitude}`

| HTTP Status	| Response										| Example
| 200 OK		| JSON object containing elevation in meters 	| {elevation: 100}
| 404 NOT FOUND	| JSON object with no data						| {}

## Technology Stack

Elevation Service uses the following technologies:

* Java 1.6 or later
* [Restlet 2.1](http://www.restlet.org) for the container and REST support
* [Spock](http://spockframework.org) and [Groovy](http://groovy.codehaus.org) for testing
* [Gradle Wrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html) for builds 


## License

This code is licensed under the [MIT License](http://opensource.org/licenses/MIT) and can be used freely.

