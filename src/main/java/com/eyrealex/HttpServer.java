package com.eyrealex;

import com.eyrealex.config.Configuration;
import com.eyrealex.config.ConfigurationManager;
import com.eyrealex.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Driver class for Http Server
 */

public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    // Main method to start the Http Server
    public static void main(String[] args) {

        // Log message indicating server starting
        LOGGER.info("Server starting...");
        // Load server configuration from configuration file
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        // Get the current configuration
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        // Log server configuration details
        LOGGER.info("Using Port: " + conf.getPort());
        LOGGER.info("Using WebRoot: " + conf.getWebroot());

        try {
            // Create and start the server listener thread
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            // If there's an IO exception during server startup, throw a runtime exception
            throw new RuntimeException(e);
        }
    }
}
