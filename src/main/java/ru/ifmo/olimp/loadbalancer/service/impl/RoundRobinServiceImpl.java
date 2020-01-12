package ru.ifmo.olimp.loadbalancer.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.ifmo.olimp.loadbalancer.domain.model.Endpoint;
import ru.ifmo.olimp.loadbalancer.service.LoadBalancerService;
import ru.ifmo.olimp.loadbalancer.service.exception.BackendOfflineException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
@Service
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

    /*** Implemented methods ***/

    @Override
    public ResponseEntity<String> redirectRequest(HttpServletRequest request) {
        // Build URI string
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(fetchEndpoint().toUri() + request.getServletPath());
        for (Map.Entry<String, String[]> param : request.getParameterMap().entrySet()) {
            uriBuilder.queryParam(param.getKey(), param.getValue());
        }

        // Headers & body
        HttpEntity<String> req;
        HttpHeaders headers = new HttpHeaders();
        StringBuilder cookies = new StringBuilder();

        for (Cookie cookie : request.getCookies()) {
            cookies.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
        }

        headers.add("Content-Type", "application/json");
        headers.add("Cookie", cookies.toString());

        try {
            req = new HttpEntity<>(request.getReader().lines().collect(Collectors.joining()), headers);
        } catch (IOException e) {
            req = new HttpEntity<>(headers);
        }

        // Redirect request and return response
        return (new RestTemplate()).exchange(
                uriBuilder.toUriString(), // URI
                HttpMethod.valueOf(request.getMethod()), // Method
                req, // Headers & body
                String.class); // Response type
    }

    @Override
    public Endpoint fetchEndpoint() {
        Endpoint endpoint = getEndpoint(index);
        int newIndex = index + 1 >= endpoints.size() ? 0 : index + 1;
        while (!isOnline(endpoint)) { // Also check if server is online
            if (index == newIndex) {
                throw new BackendOfflineException("All backend servers are offline.");
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
