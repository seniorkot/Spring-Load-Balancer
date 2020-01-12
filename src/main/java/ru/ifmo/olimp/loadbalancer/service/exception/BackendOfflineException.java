package ru.ifmo.olimp.loadbalancer.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception thrown when all backend servers are unavailable.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BackendOfflineException extends RuntimeException {

    public BackendOfflineException(String message) {
        super(message);
    }

    public BackendOfflineException(String message, Throwable cause) {
        super(message, cause);
    }
}
