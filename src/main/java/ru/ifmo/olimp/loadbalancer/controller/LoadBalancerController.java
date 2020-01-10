package ru.ifmo.olimp.loadbalancer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.olimp.loadbalancer.config.LoadBalancerProperties;
import ru.ifmo.olimp.loadbalancer.service.LoadBalancerService;
import ru.ifmo.olimp.loadbalancer.service.impl.RoundRobinServiceImpl;
import ru.ifmo.olimp.loadbalancer.service.impl.SessionPersistenceServiceImpl;
import ru.ifmo.olimp.loadbalancer.service.impl.UrlMappingServiceImpl;

import javax.annotation.PostConstruct;
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
@RequestMapping("/")
public class LoadBalancerController {

    @Autowired
    private LoadBalancerProperties properties;

    private LoadBalancerService service;

    /**
     * PostConstruct method to init service variable
     * according to mode properties value.
     */
    @PostConstruct
    public void init() {
        switch (properties.getMode()) {
            case ROUND_ROBIN:
                service = new RoundRobinServiceImpl();
                break;
            case SESSION_PERSISTENCE:
                service = new SessionPersistenceServiceImpl();
                break;
            case URL_MAPPING:
                service = new UrlMappingServiceImpl();
                break;
        }
    }

    /**
     * This method receives all API calls and redirects them to the
     * backend using Load Balancer service.
     *
     * @param request Incoming request
     * @return Received response
     */
    @RequestMapping(value = "/api/**", method = {GET, POST, PUT, DELETE})
    public ResponseEntity redirectApiRequest(HttpServletRequest request) {
        return null;
    }
}
