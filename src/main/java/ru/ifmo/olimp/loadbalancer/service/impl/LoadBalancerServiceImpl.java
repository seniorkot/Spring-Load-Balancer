package ru.ifmo.olimp.loadbalancer.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.ifmo.olimp.loadbalancer.domain.model.Endpoint;
import ru.ifmo.olimp.loadbalancer.service.LoadBalancerService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Abstract class to implement some common methods.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
abstract class LoadBalancerServiceImpl implements LoadBalancerService {

    /**
     * Ping timeout when checking servers availability.
     */
    int PING_TIMEOUT = 100; // 100 ms

    protected ResponseEntity<String> buildRequest(UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        for (Map.Entry<String, String[]> param : request.getParameterMap().entrySet()) {
            uriBuilder.queryParam(param.getKey(), param.getValue());
        }

        // Headers & body
        HttpEntity<String> req;
        HttpHeaders headers = new HttpHeaders();
        StringBuilder cookies = new StringBuilder();

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                cookies.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
            }
            headers.add("Cookie", cookies.toString());
        }
        headers.add("Content-Type", "application/json");

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
    public boolean isOnline(int index) {
        return this.isOnline(this.getEndpoint(index));
    }

    @Override
    public boolean isOnline(Endpoint endpoint) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(endpoint.getHost(), endpoint.getPort()), PING_TIMEOUT);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }
}
