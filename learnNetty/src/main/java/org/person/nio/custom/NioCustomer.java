package org.person.nio.custom;

import org.person.bio.Constants.Constants;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class NioCustomer {

    private Charset utf8 = Charset.forName("UTF-8");

    private Selector selector;

    public void initCustom() throws IOException {

        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);

        selector = Selector.open();

        int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;

        ByteBuffer readBuf = ByteBuffer.allocate(Constants.READ_LIMIT);
        ByteBuffer writeBuf = ByteBuffer.allocate(Constants.WRITE_LIMIT);
        Map<String, ByteBuffer> bufferMap = new HashMap<>();
        bufferMap.put("readBuf", readBuf);
        bufferMap.put("writeBuf", writeBuf);

        socketChannel.register(selector, interestSet, bufferMap);

        InetSocketAddress remoteAddress = new InetSocketAddress(Constants.DEFAULT_ADDRESS, Constants.DEFAULT_PORT);
        socketChannel.connect(remoteAddress);

        for (; ; ) {
            if (socketChannel.finishConnect()) break;
        }


        System.out.println("custom init success=============");
    }

    public void sendMessage() {
        try {


            while (!Thread.currentThread().isInterrupted()) {

                selector.select();

                Set<SelectionKey> keySet = selector.selectedKeys();
                Iterator<SelectionKey> it = keySet.iterator();

                while (it.hasNext()) {

                    SelectionKey key = it.next();
                    it.remove();


                    Map<String, ByteBuffer> bufferMap = (Map<String, ByteBuffer>) key.attachment();
                    ByteBuffer readBuf = bufferMap.get("readBuf");
                    ByteBuffer writeBuf = bufferMap.get("writeBuf");


                    SocketChannel socketChannel = (SocketChannel) key.channel();


                    if (key.isReadable()) {

                        socketChannel.read(readBuf);
                        readBuf.flip();

                        CharBuffer charBuffer = utf8.decode(readBuf);

                        System.out.println(charBuffer.array());
                        readBuf.clear();
                    }

                    if (key.isWritable()) {
                        Scanner sc = new Scanner(System.in);
                        System.out.println("输入");
                        String content = sc.nextLine();
                        writeBuf.put(content.getBytes("UTF-8"));
                        writeBuf.flip();
                        socketChannel.write(writeBuf);
                        writeBuf.clear();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("connect error");
            e.printStackTrace();
        } finally {
            try {
                selector.close();
            } catch (IOException e1) {
                System.out.println("selector closed failed");
                e1.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        NioCustomer nioCustomer = new NioCustomer();
        nioCustomer.initCustom();
        nioCustomer.sendMessage();
    }
}
