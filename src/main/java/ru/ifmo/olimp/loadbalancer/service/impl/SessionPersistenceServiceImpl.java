package ru.ifmo.olimp.loadbalancer.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;
import ru.ifmo.olimp.loadbalancer.domain.model.Endpoint;
import ru.ifmo.olimp.loadbalancer.service.LoadBalancerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Implementation of {@link LoadBalancerService} with
 * Session Persistence algorithm.<br>
 * Check interface documentation for more information about
 * each method purposes.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
public class SessionPersistenceServiceImpl extends RoundRobinServiceImpl {

    /**
     * Session endpoint attribute name.
     */
    private static final String SESSION_ENDPOINT_ATTRIBUTE = "endpoint";

    /*** Constructor ***/

    public SessionPersistenceServiceImpl() {
        super();
    }

    /*** Methods ***/

    @Override
    public ResponseEntity<String> redirectRequest(HttpServletRequest request) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(fetchEndpoint(request.getSession()).toUrlString() + request.getServletPath());
        return buildRequest(uriBuilder, request);
    }

    /**
     * Fetches an endpoint via Session Persistence algorithm.
     * First it checks for session attribute 'endpoint' and after
     * fetches new endpoint via Round Robin algorithm.
     *
     * @return Fetched endpoint
     */
    public Endpoint fetchEndpoint(HttpSession session) {
        if (session.getAttribute(SESSION_ENDPOINT_ATTRIBUTE) == null) {
            session.setAttribute(SESSION_ENDPOINT_ATTRIBUTE, super.fetchEndpoint());
        }
        return (Endpoint) session.getAttribute(SESSION_ENDPOINT_ATTRIBUTE);
    }
}
