package com.eyrealex.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {

    private int port;
    private String webroot;
    private ServerSocket serverSocket;
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    // Constructor for ServerListenerThread
    public ServerListenerThread(int port, String webroot) throws IOException {
        // Initialize port and webroot
        this.port = port;
        this.webroot = webroot;
        // Create a ServerSocket bound to the specified port
        this.serverSocket = new ServerSocket(this.port);
    }

    // Run method overridden from Thread
    @Override
    public void run() {
        try {
            // Continuously listen for incoming connections until serverSocket is closed
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                // Accept incoming connection
                Socket socket = serverSocket.accept();
                // Log connection accepted
                LOGGER.info(" * Connection accepted: " + socket.getInetAddress());
                // Create and start a worker thread to handle the connection
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }
        } catch (IOException e) {
            // Log and handle IOException
            LOGGER.error("Problem with communication", e);
        } finally {
            // Close the serverSocket in a final block to ensure it's always closed
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
