package com.yoterra.hippo.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
@Component
public class CheckServer {

    private final static Logger log = LoggerFactory.getLogger(CheckServer.class);

    public boolean checkServer(String serverAddress, int serverPort){

        if(!StringUtils.hasText(serverAddress))
            throw new IllegalArgumentException("server address is mandatory");

        if(serverPort == 0)
            throw new IllegalArgumentException("server port is mandatory");

        log.info("***********> Check the server : {}:{}",serverAddress,serverPort);
        boolean isAlive = false;

        // Creates a socket address from a hostname and a port number
        SocketAddress socketAddress = new InetSocketAddress(serverAddress, serverPort);
        Socket socket = new Socket();

        // Timeout required - it's in milliseconds
        int timeout = 2000;
        try {
            socket.connect(socketAddress, timeout);
            socket.close();
            isAlive = true;

        } catch (SocketTimeoutException exception) {
            log.info("SocketTimeoutException : {}. " , exception.getMessage());
        } catch (IOException exception) {
            log.info("IOException - Unable to connect to : {}. " , exception.getMessage());
        }
        return isAlive;
    }
}
