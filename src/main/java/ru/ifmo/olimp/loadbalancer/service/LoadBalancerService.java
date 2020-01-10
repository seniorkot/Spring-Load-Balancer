package ru.ifmo.olimp.loadbalancer.service;

import org.springframework.stereotype.Service;
import ru.ifmo.olimp.loadbalancer.domain.model.Endpoint;

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
     * Returns an endpoint according to selected algorithm.
     *
     * @return fetched {@link Endpoint} entity.
     */
    Endpoint getEndpoint();

    /**
     * Checks server for availability and returns the result.
     *
     * @param index Endpoint index
     * @return true - server is online
     */
    boolean isOnline(int index);
}
