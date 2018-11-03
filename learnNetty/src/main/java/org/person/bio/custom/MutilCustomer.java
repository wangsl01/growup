package org.person.bio.custom;

import org.person.bio.Constants.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MutilCustomer {

    private ExecutorService executorService = Executors.newFixedThreadPool(Constants.POOL_SIZE);

    private static Socket SocketFactory() {
        try {
            Socket socket = new Socket(Constants.DEFAULT_ADDRESS, Constants.DEFAULT_PORT);
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void sendMessage() {
        final MutilCustomer mutilCustomer = this;
        for (int i = 0; i < Constants.POOL_SIZE; i++) {
            final int id = i;
            Callable<String> callable = () -> {
                Socket socket = null;
                PrintWriter customOut = null;//
                BufferedReader customIn = null;//
                try {
                    socket = MutilCustomer.SocketFactory();
                    if (socket != null) {
                        customOut = new PrintWriter(socket.getOutputStream(), true);
                        customIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        int count = 0;
                        while (true) {
                            String message = mutilCustomer.makeMessage(id);
//                            Thread.sleep(1000);
                            count++;
                            if (count >= 3) {
                                message = "exit";
                            }
                            customOut.println("[" + id + "]:" + message);

                            if ("".equals(message) || "exit".equals(message) || "quit".equals(message)) {
                                System.out.println("结束通话：[" + id + "]");
                                break;
                            }
                            if (customIn.ready()) {
                                String returnMessage = customIn.readLine();
                                System.out.println("[" + id + "]收到响应：" + returnMessage);
                            }
                        }

                    } else {
                        throw new Exception("生产socket客户端失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (customIn != null) {
                        customIn.close();
                    }
                    if (customOut != null) {
                        customOut.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                    customIn = null;
                    customOut = null;
                    socket = null;
                }
                return null;
            };
            executorService.submit(callable);
        }
    }

    private String makeMessage(int id) {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("[" + id + "]输入：");
        Random r = new Random();
        double d = r.nextDouble();
        String str = (d * 10000) + "";
        //String str = sc.nextLine();
        System.out.println("[" + id + "]客户端输入：" + str);
        if (str == null)
            return "";
        else
            return str;
    }
}
