package com.eyrealex.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    private Socket socket;
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    // Constructor for HttpConnectionWorkerThread
    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    // Run method overridden from Thread
    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // Get input and output streams from the socket
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            // Read bytes from the input stream until end of stream is reached
            int _byte;
            while ((_byte = inputStream.read()) >= 0) {
                System.out.print((char) _byte);
            }

            // Prepare the HTTP response body (a simple HTML page)
            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>Hello, World!</h1></body></html>";

            // Define carriage return and line feed characters
            final String CRLF = "\r\n";

            // Construct the HTTP response
            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line: HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF + //HEADER
                            CRLF +
                            html +
                            CRLF + CRLF;

            // Write the HTTP response to the output stream
            outputStream.write(response.getBytes());

            // Simulate processing delay
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                // Throw a runtime exception if sleep is interrupted
                throw new RuntimeException(e);
            }

            // Log message indicating connection processing finished
            LOGGER.info(" * Connection Processing Finished.");

        } catch (IOException e) {
            // Log and print stack trace if there's a problem with communication
            LOGGER.error("Problem with communication", e);
            e.printStackTrace();
        } finally {
            // Close streams and socket in a final block to ensure resources are released
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
