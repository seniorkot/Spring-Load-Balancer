package ru.ifmo.olimp.loadbalancer.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.olimp.loadbalancer.service.LoadBalancerService;

/**
 * This REST controller receives HTTP requests and redirects
 * them to the backend server using {@link LoadBalancerService}
 * methods.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@RestController
public class LoadBalancerController {

}
