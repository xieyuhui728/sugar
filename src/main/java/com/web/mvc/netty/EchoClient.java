package com.web.mvc.netty;

import com.web.mvc.constant.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Echo客户端
 * Created by xieyuhui on 2018/8/24.
 * <p>
 * description:
 * >.为初始化客户端，创建了一个Bootstrap实例
 * >.为进行事件处理分配了一个NioEventLoopGroup实例，其中事件处理器包括创建新的连接以及处理入站和出站数据
 * >.为服务器连接创建一个InetSocketAddress实例
 * >.当连接被建立时，一个EchoClientHandler实例会被安装到ChannelPipeline中
 * >.在一切都设置完成后，调用Bootstrap.connect()方法连接到远程节点
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();//创建Bootstrap
            bootstrap.group(group)//指定NioEventLoopGroup以处理客户端事件
                    .channel(NioSocketChannel.class)//适用于NIO传输的Channel类型
                    .remoteAddress(new InetSocketAddress(host, port))//设置服务器的InetSocketAddress
                    .handler(new ChannelInitializer<SocketChannel>() {//在创建Channel时，向ChannelPipeline中添加一个EchoClientHandler实例
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();//连接到远程节点，阻塞等待直到连接完成
            future.channel().closeFuture().sync();//阻塞，直到Channel关闭
        } finally {
            group.shutdownGracefully().sync();//关闭线程池并且释放所有的资源
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoClient(Constants.HOST, Constants.PORT).start();
    }
}
