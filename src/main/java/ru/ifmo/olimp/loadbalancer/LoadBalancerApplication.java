package ru.ifmo.olimp.loadbalancer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application main class.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class LoadBalancerApplication implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(LoadBalancerApplication.class);

    /**
     * Main method.<br>
     * Runs Spring Boot application.
     *
     * @param args App arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(LoadBalancerApplication.class, args);
    }

    /**
     * Application method.<br>
     * Checks & handles command line arguments.
     *
     * @param args Application arguments
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        TODO: Handle arguments
    }
}
