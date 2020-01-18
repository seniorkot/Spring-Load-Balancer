package ru.ifmo.olimp.loadbalancer.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.ifmo.olimp.loadbalancer.domain.model.Endpoint;
import ru.ifmo.olimp.loadbalancer.service.LoadBalancerService;
import ru.ifmo.olimp.loadbalancer.service.exception.BackendOfflineException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link LoadBalancerService} with
 * Round Robin algorithm.<br>
 * Check interface documentation for more information about
 * each method purposes.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@Service(value = "round_robin")
public class RoundRobinServiceImpl extends LoadBalancerServiceImpl {

    /**
     * Index of next server.
     */
    private int index;

    /**
     * Array list of endpoints.
     */
    private List<Endpoint> endpoints;

    /*** Constructor ***/

    public RoundRobinServiceImpl() {
        this.index = 0;
        this.endpoints = new ArrayList<>();
    }

    /*** Methods ***/

    @Override
    public ResponseEntity<String> redirectRequest(HttpServletRequest request) {
        // Build URI string
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(fetchEndpoint().toUrlString() + request.getServletPath());
        return buildRequest(uriBuilder, request);
    }

    /**
     * Fetches an endpoint via Round Robin algorithm.
     *
     * @return Fetched endpoint
     */
    public Endpoint fetchEndpoint() {
        Endpoint endpoint = getEndpoint(index);
        int newIndex = index + 1 >= endpoints.size() ? 0 : index + 1;
        while (!isOnline(endpoint)) { // Also check if server is online
            if (index == newIndex) {
                throw new BackendOfflineException("All backend servers are unavailable.");
            }
            endpoint = getEndpoint(newIndex++);
            newIndex = newIndex >= endpoints.size() ? 0 : newIndex;
        }
        index = newIndex;
        return endpoint;
    }

    @Override
    public List<Endpoint> getEndpoints() {
        return this.endpoints;
    }

    @Override
    public Endpoint getEndpoint(int index) {
        return this.endpoints.get(index);
    }

    @Override
    public void setEndpoints(List<Endpoint> endpoints) {
        this.endpoints.addAll(endpoints);
    }

    @Override
    public void addEndpoint(Endpoint endpoint) {
        this.endpoints.add(endpoint);
    }

    @Override
    public void removeEndpoint(int index) {
        this.endpoints.remove(index);
    }
}
