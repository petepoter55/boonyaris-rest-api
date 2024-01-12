package com.rest.api.boonyarisRestApi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"com.rest.api.boonyarisRestApi"})
public class BoonyarisRestApiApplication {
    private static final Logger logger = LogManager.getLogger(BoonyarisRestApiApplication.class);

    static {
        try {
            System.setProperty("hostName", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        logger.debug("Debug message");
        logger.info("Info message");
        logger.warn("Warning message");
        logger.error("Error message");
        SpringApplication.run(BoonyarisRestApiApplication.class, args);
    }

}
