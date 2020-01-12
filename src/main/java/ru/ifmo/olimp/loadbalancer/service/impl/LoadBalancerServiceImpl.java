package ru.ifmo.olimp.loadbalancer.service.impl;

import ru.ifmo.olimp.loadbalancer.domain.model.Endpoint;
import ru.ifmo.olimp.loadbalancer.service.LoadBalancerService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

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
