package com.eyrealex.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.eyrealex.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    // Create a singleton instance of ConfigurationManager
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    // Private constructor to prevent instantiation from outside
    private ConfigurationManager() {
    }

    // Get an instance of ConfigurationManager
    public static ConfigurationManager getInstance(){
        // If no instance exists, create a new one
        if (myConfigurationManager == null){
            myConfigurationManager = new ConfigurationManager();
        }
        // Return the singleton instance
        return myConfigurationManager;
    }

    // Load the configuration file specified by the filePath parameter
    public void loadConfigurationFile(String filePath){
        FileReader fileReader = null;
        // Attempt to open the file specified by the filePath
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            // If the file is not found, throw a HttpConfigurationException
            throw new HttpConfigurationException(e);
        }
        // Read the file content into a StringBuffer
        StringBuffer sb = new StringBuffer();
        int i;
        // Read character by character from the file
        try {
            while ((i = fileReader.read()) != -1){
                sb.append((char)i);
            }
        } catch (IOException e) {
            // If an IOException occurs during file reading, throw a HttpConfigurationException
            throw new HttpConfigurationException(e);
        }
        JsonNode conf = null;
        // Parse the JSON content of the file into a JsonNode
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            // If an error occurs during JSON parsing, throw a HttpConfigurationException with appropriate message
            throw new HttpConfigurationException("Error parsing the configuration file", e);
        }
        // Convert the JsonNode to a Configuration object
        try{
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        }catch (JsonProcessingException e){
            // If an error occurs during JSON deserialization, throw a HttpConfigurationException with appropriate message
            throw new HttpConfigurationException("Error parsing the configuration file, internal", e);
        }
    }

    // Return the current loaded configuration
    public Configuration getCurrentConfiguration() throws HttpConfigurationException {
        // If no current configuration is set, throw a HttpConfigurationException
        if(myCurrentConfiguration == null){
            throw new HttpConfigurationException("No current configuration set.");
        }
        // Return the current configuration
        return myCurrentConfiguration;
    }
}
