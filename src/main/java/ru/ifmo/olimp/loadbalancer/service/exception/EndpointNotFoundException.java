package ru.ifmo.olimp.loadbalancer.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception thrown when no matching endpoint found.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EndpointNotFoundException extends RuntimeException {

    public EndpointNotFoundException(String message) {
        super(message);
    }

    public EndpointNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
