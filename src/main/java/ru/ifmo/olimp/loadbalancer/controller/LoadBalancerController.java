package ru.ifmo.olimp.loadbalancer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.olimp.loadbalancer.config.LoadBalancerProperties;
import ru.ifmo.olimp.loadbalancer.service.LoadBalancerService;
import ru.ifmo.olimp.loadbalancer.service.impl.RoundRobinServiceImpl;
import ru.ifmo.olimp.loadbalancer.service.impl.SessionPersistenceServiceImpl;
import ru.ifmo.olimp.loadbalancer.service.impl.UrlMappingServiceImpl;

import javax.annotation.PostConstruct;

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
}
