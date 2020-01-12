package ru.ifmo.olimp.loadbalancer.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.ifmo.olimp.loadbalancer.domain.model.Endpoint;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * This interface describes a load balancer
 * request processing service.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@Service
public interface LoadBalancerService {

    /**
     * Redirects incoming requests and returns responses from
     * backend servers.
     *
     * @param request Incoming request
     * @return Response from backend
     */
    ResponseEntity<String> redirectRequest(HttpServletRequest request);

    /**
     * Returns a full list of endpoint.
     *
     * @return list of {@link Endpoint} entities
     */
    List<Endpoint> getEndpoints();

    /**
     * Returns an endpoint by index.
     *
     * @param index Endpoint index
     * @return {@link Endpoint} entity
     */
    Endpoint getEndpoint(int index);

    /**
     * Sets endpoints variable.
     *
     * @param endpoints List of endpoints
     */
    void setEndpoints(List<Endpoint> endpoints);

    /**
     * Adds an endpoint to list.
     *
     * @param endpoint New endpoint
     */
    void addEndpoint(Endpoint endpoint);

    /**
     * Removes an endpoint from list.
     *
     * @param index Endpoint index
     */
    void removeEndpoint(int index);

    /**
     * Checks server for availability and returns the result.
     *
     * @param index Endpoint index
     * @return true - server is online
     */
    boolean isOnline(int index);

    /**
     * Establishes test connection to the server to determine if
     * it is online.
     *
     * @param endpoint Endpoint
     * @return true - server is online
     */
    boolean isOnline(Endpoint endpoint) throws IOException;
}
