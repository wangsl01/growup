package org.person.bio.server;

import jdk.nashorn.internal.codegen.CompilerConstants;
import org.person.bio.Constants.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MutilServer {

    private ExecutorService executorService = Executors.newFixedThreadPool(Constants.POOL_SIZE);

    private static ServerSocket serverSocketFactory() {
        try {
            ServerSocket serverSocket = new ServerSocket(Constants.DEFAULT_PORT);
            return serverSocket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void recieveMessage() {

        final ServerSocket serverSocket =  this.serverSocketFactory();

        for (int i = 0; i < Constants.POOL_SIZE;i++) {
            Runnable runnable = () -> {
                Socket socket = null;
                PrintWriter serverOut = null;
                BufferedReader serverIn = null;
                try {
//                    Thread.sleep(10);
                    socket = serverSocket.accept();
                    serverOut = new PrintWriter(socket.getOutputStream(), true);
                    serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (true) {
                        if(serverIn.ready()) {
                            String message = serverIn.readLine();
                            System.out.println("服务端接收:" + message);
                            serverOut.println(message + "======");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (serverIn != null) {
                            serverIn.close();
                        }
                        if (serverOut != null) {
                            serverOut.close();
                        }
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    serverIn = null;
                    serverOut = null;
                    socket = null;
                }
            };
            executorService.submit(runnable);
//            Thread t = new Thread(runnable);
//            t.start();
        }

    }


}
