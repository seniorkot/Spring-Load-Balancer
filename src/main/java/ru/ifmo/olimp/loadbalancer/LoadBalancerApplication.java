package ru.ifmo.olimp.loadbalancer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.ifmo.olimp.loadbalancer.config.LoadBalancerProperties;
import ru.ifmo.olimp.loadbalancer.domain.model.Endpoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Application main class.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@EnableConfigurationProperties({LoadBalancerProperties.class})
public class LoadBalancerApplication implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoadBalancerProperties properties;

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
        if (args.containsOption("config")) {
            List<Endpoint> endpointList = new ArrayList<>();
            for (String value : args.getOptionValues("config")) {
                File file = new File(value);
                if (file.exists()) {
                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);
                    String line;
                    while ((line = br.readLine()) != null) {
                        try {
                            endpointList.add(Endpoint.parseUriString(line));
                        } catch (URISyntaxException e) {
                            logger.warn("Incorrect URI syntax: " + line);
                        }
                    }
                    br.close();
                    fr.close();
                }
                else {
                    logger.warn("File " + value + " doesn't exist.");
                }
                if (endpointList.size() > 0) {
                    properties.setEndpoints(endpointList);
                }
            }
        }
    }
}
