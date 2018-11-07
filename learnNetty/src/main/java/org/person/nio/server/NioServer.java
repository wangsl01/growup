package org.person.nio.server;


import org.person.bio.Constants.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class NioServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    private static NioServer nioServer;

    private Charset utf8 = Charset.forName("UTF-8");


    private NioServer(int port) {
        try {
            selector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            InetSocketAddress localAddress = new InetSocketAddress(port);
            serverSocketChannel.bind(localAddress, Constants.BACKLOG);

            System.out.println("server start success with port : " + port);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            System.out.println("server start fail with port：" + port);
            e.printStackTrace();
            selector = null;
            serverSocketChannel = null;
        }
    }

    public void recieveMessage() {
        /*服务器线程被中断后会退出*/
        try {
            for (; ; ) {

                selector.select();
                Set<SelectionKey> keySet = selector.selectedKeys();
                Iterator<SelectionKey> it = keySet.iterator();
                SelectionKey key;

                while (it.hasNext()) {
                    key = it.next();
                    it.remove();

                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);

                        //构建BUFFER
                        ByteBuffer readBuf = ByteBuffer.allocate(Constants.READ_LIMIT);
                        ByteBuffer writeBuf = ByteBuffer.allocate(Constants.WRITE_LIMIT);
                        Map<String, ByteBuffer> bufferMap = new HashMap<>();
                        bufferMap.put("readBuf", readBuf);
                        bufferMap.put("writeBuf", writeBuf);

                        socketChannel.register(selector, SelectionKey.OP_READ, bufferMap);

                        System.out.println("register success：" + socketChannel.getRemoteAddress());
                    }


                    if (key.isReadable()) {

                        Map<String, ByteBuffer> bufferMap = (Map<String, ByteBuffer>) key.attachment();
                        ByteBuffer readBuf = bufferMap.get("readBuf");
                        ByteBuffer writeBuf = bufferMap.get("writeBuf");

                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.read(readBuf);
                        readBuf.flip();

                        CharBuffer charBuffer = utf8.decode(readBuf);
//                        String content = new String(charBuffer.array());
                        System.out.println(charBuffer.array());
                        readBuf.rewind();


                        writeBuf.put("recieved message：".getBytes("UTF-8"));
                        writeBuf.put(readBuf);

                        readBuf.clear();

                        key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);

                    }

                    /*通道感兴趣写事件且底层缓冲区有空闲*/
                    if (key.isWritable()) {

                        Map<String, ByteBuffer> bufferMap = (Map<String, ByteBuffer>) key.attachment();
                        ByteBuffer writeBuf = bufferMap.get("writeBuf");


                        writeBuf.flip();

                        SocketChannel sc = (SocketChannel) key.channel();

                        int len = 0;
                        while (writeBuf.hasRemaining()) {
                            len = sc.write(writeBuf);
                            /*说明底层的socket写缓冲已满*/
                            if (len == 0) {
                                break;
                            }
                        }

                        writeBuf.compact();

                        /*说明数据全部写入到底层的socket写缓冲区*/
                        if (len != 0) {
                            /*取消通道的写事件*/
                            key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
                        }

                    }
                }
            }
        } catch (IOException e1) {
            System.out.println("server error");
            e1.printStackTrace();
        } finally {
            try {
                selector.close();
            } catch (IOException e) {
                System.out.println("selector close failed");
                e.printStackTrace();
            }
        }
    }


    public enum Singleton {
        INSTANCE;

        Singleton() {
//            this(Constants.DEFAULT_PORT);
            NioServer.nioServer = new NioServer(Constants.DEFAULT_PORT);
        }

//        private Singleton(int port) {
//            NioServer.nioServer = new NioServer(port);
//        }

        public NioServer getInstant() {
            return NioServer.nioServer;
        }
    }

//    public static void main(String[] args) {
//        System.out.println(Singleton.INSTANCE.getInstant());
//    }

    public static void main(String[] args) {
        NioServer nioServer = Singleton.INSTANCE.getInstant();
        nioServer.recieveMessage();
    }

}
