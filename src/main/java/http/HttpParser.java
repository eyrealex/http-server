package http;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    // Define constants for special characters in HTTP requests
    private static final int SP = 0x20; // ASCII code for space character
    private static final int CR = 0x0D; // ASCII code for carriage return
    private static final int LF = 0x0A; // ASCII code for line feed

    // Parse the incoming HTTP request
    public HttpRequest parserHttpRequest(InputStream inputStream) {

        // Create a reader to read the input stream using US_ASCII encoding
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        // Create a new HttpRequest object to store the parsed request
        HttpRequest request = new HttpRequest();

        try {
            // Parse the request line
            parserRequestLine(streamReader, request);
        } catch (IOException e) {
            // If an IOException occurs, wrap it in a RuntimeException and throw
            throw new RuntimeException(e);
        }

        // Parse the headers
        parserHeaders(streamReader, request);

        try {
            // Parse the body
            parserBody(streamReader, request);
        } catch (IOException e) {
            // If an IOException occurs, wrap it in a RuntimeException and throw
            throw new RuntimeException(e);
        }

        // Return the parsed HttpRequest object
        return request;
    }

    // Parse the request line of the HTTP request
    private void parserRequestLine(InputStreamReader streamReader, HttpRequest request) throws IOException {
        // StringBuilder to store the data being processed
        StringBuilder processingDataBuffer = new StringBuilder();
        int _byte;

        // Read characters until end of stream is reached
        while ((_byte = streamReader.read()) >= 0) {
            // Check for carriage return (CR)
            if (_byte == CR) {
                // Read the next character after CR
                _byte = streamReader.read();
                // If the next character is line feed (LF), the request line has been processed
                if (_byte == LF) {
                    // Log the processed request line
                    LOGGER.debug("Request Line to Process : {}", processingDataBuffer.toString());
                    return;
                }
            }

            // Check for space character (SP)
            if (_byte == SP){
                // Log the processed request line
                LOGGER.debug("Request Line to Process : {}", processingDataBuffer.toString());
                // Clear the processing data buffer for the next part of the request line
                processingDataBuffer.delete(0, processingDataBuffer.length());

            } else {
                // Append the character to the processing data buffer
                processingDataBuffer.append((char) _byte);
            }
        }
    }

    // Parse the headers of the HTTP request
    private void parserHeaders(InputStreamReader streamReader, HttpRequest request) {
        // Implementation of parsing headers is missing in this example
        // You would need to implement logic to parse and store headers in the HttpRequest object
    }

    // Parse the body of the HTTP request
    private void parserBody(InputStreamReader streamReader, HttpRequest request) throws IOException {
        // Implementation of parsing body is missing in this example
        // You would need to implement logic to parse and store the body of the request
    }
}
