package ru.ifmo.olimp.loadbalancer.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.ifmo.olimp.loadbalancer.domain.model.Endpoint;
import ru.ifmo.olimp.loadbalancer.service.LoadBalancerService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link LoadBalancerService} with
 * URL Mapping algorithm.<br>
 * Check interface documentation for more information about
 * each method purposes.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@Service
public class UrlMappingServiceImpl extends LoadBalancerServiceImpl {

    private Map<String, Endpoint> endpoints = new HashMap<>();

    @Override
    public ResponseEntity<String> redirectRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    public Endpoint fetchEndpoint() {
        return null;
    }

    @Override
    public List<Endpoint> getEndpoints() {
        return null;
    }

    @Override
    public Endpoint getEndpoint(int index) {
        return null;
    }

    @Override
    public void setEndpoints(List<Endpoint> endpoints) {

    }

    @Override
    public void addEndpoint(Endpoint endpoint) {

    }

    @Override
    public void removeEndpoint(int index) {

    }
}
