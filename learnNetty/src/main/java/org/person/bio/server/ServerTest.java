package org.person.bio.server;

import org.person.bio.Constants.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {

    public void startServer() {
//        for(int i=0;i<3;i++) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        BufferedReader serverBr = null;
        PrintWriter serverPw = null;
        try {
            serverSocket = new ServerSocket(Constants.DEFAULT_PORT);
            while (true) {
                socket = serverSocket.accept();
                serverPw = new PrintWriter(socket.getOutputStream(), true);
                serverBr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String content = serverBr.readLine();
                if (content == null) break;
                System.out.println("服务方接收------" + content);
                serverPw.println("123123123-----" + content + "=0=0=0=0=0=");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverBr != null) {
                    serverBr.close();
                }
                if (serverPw != null) {
                    serverPw.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    }
}
