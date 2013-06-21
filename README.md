#Elevation Service

Elevation Service is a RESTFul web service that returns elevation data for any lat/long in North America.

##How it works

Elevation Service will download height files automatically from the USGS's file server, parse them, and return elevation data.

##Usage

**Prerequisites: JDK 1.6 or later.**

Clone the repository and change into it.

```
    git clone https://github.com/asinesio/elevation-service.git
    cd elevation-service
```

Use gradle to run the app locally:

```
    ./gradlew run
```

If all went well, the app will begin listening at `http://localhost:3000`.

## API

### elevation

Return elevation data for a given latitude/longitude. Example: `http://localhost:3000/elevation/37.331789/-122.029620`

<table>
    <thead>
        <tr>
            <th>Method</th>
            <th>Response Content Types</th>
            <th>URL Format</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>GET</td>
            <td>application/json</td>
            <td>http://localhost:3000/elevation/{latitude}/{longitude}</td>
        </tr>
    </tbody>
</table>


<table>
    <thead>
        <tr>
            <th>HTTP Status</th>
            <th>Response</th>
            <th>Example</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>200 OK</td>
            <td>JSON object containing elevation in meters</td>
            <td>{"elevation":100,"units":"meters"}</td>
        </tr>
        <tr>
            <td>404 NOT FOUND</td>
            <td>JSON object with no data</td>
            <td>{}</td>
        </tr>
</table>

## Technology Stack

Elevation Service uses the following technologies:

* Java 1.6 or later
* [Restlet 2.1](http://www.restlet.org) for the container and REST support
* [Spring 3.0](http://www.springframework.org) for dependency injection
* [Spock](http://spockframework.org) and [Groovy](http://groovy.codehaus.org) for testing
* [Gradle Wrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html) for builds 


## License

This code is licensed under the [MIT License](http://opensource.org/licenses/MIT) and can be used freely.

