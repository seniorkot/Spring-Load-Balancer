package ru.ifmo.olimp.loadbalancer.domain.model;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

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

    /*** Methods ***/

    @Override
    public String toString() {
        return "http://" + this.host + ":" + this.port + (this.path != null ? this.path : "");
    }

    public String toUrlString() {
        return "http://" + this.host + ":" + this.port;
    }

    /**
     * Parses URI string and returns new Endpoint object.
     *
     * @param uriStr URI string
     * @return Created Endpoint object
     * @throws URISyntaxException Incorrect URI syntax exception
     */
    public static Endpoint parseUriString(String uriStr) throws URISyntaxException {
        return parseUri(new URI(uriStr));
    }

    /**
     * Parses URI and returns new {@link Endpoint} object.
     *
     * @param uri URI object
     * @return Created Endpoint object
     */
    public static Endpoint parseUri(URI uri) {
        Endpoint endpoint = new Endpoint();
        endpoint.setHost(uri.getHost());
        endpoint.setPort(uri.getPort());
        endpoint.setPath(uri.getPath());
        return endpoint;
    }
}
