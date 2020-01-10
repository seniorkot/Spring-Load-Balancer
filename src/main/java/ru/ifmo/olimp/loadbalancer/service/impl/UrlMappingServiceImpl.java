package ru.ifmo.olimp.loadbalancer.service.impl;

import org.springframework.stereotype.Service;
import ru.ifmo.olimp.loadbalancer.domain.model.Endpoint;
import ru.ifmo.olimp.loadbalancer.service.LoadBalancerService;

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
public class UrlMappingServiceImpl implements LoadBalancerService {

    @Override
    public Endpoint getEndpoint() {
        return null;
    }

    @Override
    public boolean isOnline(int index) {
        return false;
    }
}
