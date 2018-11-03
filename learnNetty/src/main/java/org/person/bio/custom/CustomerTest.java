package org.person.bio.custom;

import org.person.bio.Constants.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CustomerTest {

    public void startCustom() {
        for (int i = 0; i < 3; i++) {
            Socket socket = null;
            PrintWriter customPw = null;
            BufferedReader customBr = null;
            try {
                socket = new Socket(Constants.DEFAULT_ADDRESS, Constants.DEFAULT_PORT);
                customPw = new PrintWriter(socket.getOutputStream(), true);
                customBr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            while(true) {
                System.out.println(i);
                customPw.println("调用服务方：" + System.currentTimeMillis());
                System.out.println("得到服务方结果：" + customBr.readLine());
//            }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (customPw != null) {
                        customPw.close();
                    }
                    if (customBr != null) {
                        customBr.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
