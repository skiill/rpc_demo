package com.xsj.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.ByteBuffer;

@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuffer> {
    private Class<?> serverClass;
    public EchoClientHandler() {
    }
    public EchoClientHandler(Class<?> serverClass) {
        this.serverClass = serverClass;

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        final ChannelHandlerContext ctx1 = ctx;
        Proxy.newProxyInstance(serverClass.getClassLoader(), new Class<?>[]{}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = new Invocation(serverClass,method,args);
                return ctx1.writeAndFlush(invocation);
            }
        });

    }

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuffer byteBuffer) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
