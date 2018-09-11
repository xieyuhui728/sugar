package com.web.mvc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by xieyuhui on 2018/8/24.
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 在到服务器的连接已经建立之后将被调用
     * 当被通知Channel是活跃的时候，发送一条消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hi~来自客户端的一次问候!", CharsetUtil.UTF_8));
    }

    /**
     * 当从服务器接收到一条消息时调用
     * 记录已接收消息的转储
     *
     * @param ctx
     * @param in
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        System.out.printf("Client received:" + in.toString(CharsetUtil.UTF_8));
    }

    /**
     * 在处理过程中引发异常时调用
     * 在发生异常时，记录错误并关闭Channel
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
