Spring Boot Load Balancer application
=
Spring Boot application implementing 3 Load Balancer algorithms: Round Robin, Session Persistence &amp; URL Mapping.

## Build
<i>From project root directory</i>
* Pure Maven: `mvn clean package`<br>

## Run
* Built jar file (<i>./target dir)</i>: `java -jar landing-1.0.jar [--port] [--mode]`<br>
* Spring Boot Maven plugin (<i>root dir</i>): `mvn spring-boot:run`

### Arguments:
<b>port</b> - application server port. Default: <i>8080</i><br>
<b>mode</b> - Load Balancer mode:
* round_robin - Round Robin
* session_persistence - Session Persistence (<i>Default mode</i>)
* url_mapping - URL Mapping

## Application properties
[application.properties](https://github.com/seniorkot/Spring-Load-Balancer/blob/master/src/main/resources/application.properties)<br>
* <b>spring.profiles.active</b> - application profile. Current: </i>dev</i>. Change to <i>prod</i> if running in Production mode.<br>
* <b>server.port</b> - application default server port.<br>
* <b>loadbalancer.mode</b> - Load Balancer mode. See arguments above.

[application-dev.properties](https://github.com/seniorkot/Spring-Load-Balancer/blob/master/src/main/resources/application-dev.properties) / application-prod.properties<br>

For current version this file contains only a list of endpoints:<br>
`loadbalancer.endpoints[0].host=localhost`<br>
`loadbalancer.endpoints[0].port=8081`<br>
`loadbalancer.endpoints[0].port=/api/music`<br>

## Application modes
### Round Robin
With new request an endpoint next in list is fetched (cyclically).<br>

To use this algorithm provide at least one endpoint with <i>host</i> & <i>port</i>.
### Session Persistence
Same algorithm as Round Robin except that for each session a concrete endpoint is saved. As the result, while session is alive, all user request are sent to one backend server.<br>

To use this algorithm provide at least one endpoint with <i>host</i> & <i>port</i>.
### URL Mapping
Backend servers are mapped with URI path. For example: every request like `curl http://example.com:8080/api/music` will be redirected to one backend server while `curl http://example.com:8080/api/video` will be redirected to another.

To use this algorithm provide at least one endpoint with <i>host</i>, <i>port</i> & <i>path</i>.
