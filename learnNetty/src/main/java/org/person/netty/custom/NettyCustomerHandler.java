package org.person.netty.custom;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Scanner;

public class NettyCustomerHandler extends ChannelInboundHandlerAdapter {

    private ByteBuf message ;

    private Object lock = new Object();

    public NettyCustomerHandler(){
        this.message = Unpooled.buffer(1024);
        byte[] req = "456".getBytes();
//        message = Unpooled.buffer(req.length);
        message.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        ctx.writeAndFlush(message);

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String body = new String(req,"UTF-8");
        System.out.println("Now is:"+ body);
//        lock.notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    public void sendMessage(){
        Scanner scanner = new Scanner(System.in);
        String content ;
//        for(;;){
            System.out.println("输入:");
            content = scanner.nextLine();
            byte[] req = content.getBytes();
            message.writeBytes(req);
//            synchronized (lock){
//                try {
//                    lock.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

}
