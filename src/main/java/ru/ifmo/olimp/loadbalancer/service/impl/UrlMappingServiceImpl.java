package ru.ifmo.olimp.loadbalancer.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.ifmo.olimp.loadbalancer.domain.model.Endpoint;
import ru.ifmo.olimp.loadbalancer.service.LoadBalancerService;
import ru.ifmo.olimp.loadbalancer.service.exception.BackendOfflineException;
import ru.ifmo.olimp.loadbalancer.service.exception.EndpointNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

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
@Service(value = "url_mapping")
public class UrlMappingServiceImpl extends LoadBalancerServiceImpl {

    private Map<String, List<Endpoint>> endpoints;

    /*** Constructor ***/

    public UrlMappingServiceImpl() {
        this.endpoints = new HashMap<>();
    }

    /*** Methods ***/

    @Override
    public ResponseEntity<String> redirectRequest(HttpServletRequest request) {
        // Build URI string
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(fetchEndpoint(request.getServletPath()).toUrlString() + request.getServletPath());
        return buildRequest(uriBuilder, request);
    }

    /**
     * Fetches an endpoint via URL Mapping algorithm.
     *
     * @param path URI path
     * @return Fetched endpoint
     */
    public Endpoint fetchEndpoint(String path) {
        // Check regex and put endpoints to the set
        Set<List<Endpoint>> endpointsSet = endpoints.entrySet()
                .stream()
                .filter(entry -> path.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
        if (endpointsSet.isEmpty()) {
            throw new EndpointNotFoundException("No endpoint with path " + path + " found.");
        }
        for (Endpoint endpoint : endpointsSet.iterator().next()) {
            if (isOnline(endpoint)) {
                return endpoint;
            }
        }
        throw new BackendOfflineException("All backend servers are unavailable.");
    }

    @Override
    public List<Endpoint> getEndpoints() {
        List<Endpoint> endpoints = new ArrayList<>();
        for (List<Endpoint> list : this.endpoints.values()) {
            endpoints.addAll(list);
        }
        return endpoints;
    }

    @Override
    public Endpoint getEndpoint(int index) {
        // TODO: Get endpoint
        return null;
    }

    @Override
    public void setEndpoints(List<Endpoint> endpoints) {
        for (Endpoint endpoint : endpoints) {
            addEndpoint(endpoint);
        }
    }

    @Override
    public void addEndpoint(Endpoint endpoint) {
        if (!this.endpoints.containsKey(endpoint.getPath())) {
            List<Endpoint> list = new ArrayList<>();
            list.add(endpoint);
            this.endpoints.put(endpoint.getPath(), list);
        }
        else {
            this.endpoints.get(endpoint.getPath()).add(endpoint);
        }
    }

    @Override
    public void removeEndpoint(int index) {
        // TODO: Remove endpoint
    }
}
