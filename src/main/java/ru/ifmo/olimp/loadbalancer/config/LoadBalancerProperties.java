package ru.ifmo.olimp.loadbalancer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.ifmo.olimp.loadbalancer.domain.enums.Mode;
import ru.ifmo.olimp.loadbalancer.domain.model.Endpoint;

import java.util.List;

/**
 * Load Balancer properties (like mode & endpoints).
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@ConfigurationProperties(prefix = "loadbalancer")
public class LoadBalancerProperties {

    /**
     * Application mode: see {@link Mode}.
     */
    private Mode mode;

    /**
     * Load Balancer endpoints: see {@link Endpoint}.
     */
    private List<Endpoint> endpoints;

    /*** Getters ***/

    public Mode getMode() {
        return mode;
    }

    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    /*** Setters ***/

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setEndpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }
}
