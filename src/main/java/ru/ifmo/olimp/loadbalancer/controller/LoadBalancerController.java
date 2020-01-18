package ru.ifmo.olimp.loadbalancer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import ru.ifmo.olimp.loadbalancer.config.LoadBalancerProperties;
import ru.ifmo.olimp.loadbalancer.service.LoadBalancerService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * This REST controller receives HTTP requests and redirects
 * them to the backend server using {@link LoadBalancerService}
 * methods.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@RestController
public class LoadBalancerController {

    @Resource(name = "${loadbalancer.mode}")
    private LoadBalancerService service;

    private final LoadBalancerProperties properties;

    @Autowired
    public LoadBalancerController(LoadBalancerProperties properties) {
        this.properties = properties;
    }

    /**
     * PostConstruct method to set endpoints in service.
     */
    @PostConstruct
    public void init() {
        service.setEndpoints(properties.getEndpoints());
    }

    /**
     * This method receives all API calls and redirects them to the
     * backend using Load Balancer service. Client & Server error
     * handling provided.
     *
     * @param request Incoming request
     * @return Received response
     */
    @RequestMapping(value = "/api/**", method = {GET, POST, PUT, DELETE})
    public ResponseEntity<String> redirectApiRequest(HttpServletRequest request) {
        ResponseEntity<String> response;
        try {
            response = service.redirectRequest(request);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            response = ResponseEntity.status(e.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getResponseBodyAsString());
        }
        return response;
    }
}
