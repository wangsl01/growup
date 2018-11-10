package org.person.netty.custom;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.person.bio.Constants.Constants;

import static java.lang.Thread.sleep;

public class NettyCustomer {

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            NettyCustomerHandler nettyCustomerHandler = new NettyCustomerHandler();
                            ch.pipeline().addLast(nettyCustomerHandler);
                            //不理解此处为什么不能用循环来发送信息（只在服务端看到第一条信息，后面都不能收到）？不理解为什么同一个对象被addLast执行两次会报错？
//                            for(int i=0;i<10;i++) {
//                                NettyCustomerHandler nettyCustomerHandler = new NettyCustomerHandler();
//                                nettyCustomerHandler.sendMessage();
//                                ch.pipeline().addLast(nettyCustomerHandler);
//                            }
                        }
                    });

            ChannelFuture channelFuture = b.connect(host, port).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new NettyCustomer().connect(Constants.DEFAULT_PORT,Constants.DEFAULT_ADDRESS);
    }
}
