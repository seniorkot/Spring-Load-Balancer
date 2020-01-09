package ru.ifmo.olimp.loadbalancer.domain.model;

import java.io.Serializable;

/**
 * Endpoint entity class.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
public class Endpoint implements Serializable {

    /**
     * Endpoint host.
     */
    private String host;

    /**
     * Host port number.
     */
    private int port;

    /**
     * Endpoint URL-path (used on URL mapping).
     */
    private String path;

    /*** Getters ***/

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    /*** Setters ***/

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
