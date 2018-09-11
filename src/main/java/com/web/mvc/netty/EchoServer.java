package com.web.mvc.netty;

import com.web.mvc.constant.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Echo服务端
 * Created by xieyuhui on 2018/8/24.
 * <p>
 * description：
 * >.EchoServerHandler实现了业务逻辑
 * >.main()方法引导了服务器
 * 引导过程步骤如下：
 * >>.创建一个ServerBootstrap的实例以引导和绑定服务器。
 * >>.创建一个NioEventLoopGroup实例以进行事件的处理，如接受新链接以及读/写数据。
 * >>.指定服务器绑定的本地的InetSocketAddress。
 * >>.使用EchoServerHandler的实例初始化每一个新的Channel。
 * >>.调用ServerBootstrap.bind()方法以绑定服务器。
 */
public class EchoServer {
    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();//创建EventLoopGroup
        try {
            final ServerBootstrap bootstrap = new ServerBootstrap();//创建ServerBootstrap
            bootstrap.group(group).channel(NioServerSocketChannel.class)//指定使用NIO的Channel
                    .localAddress(new InetSocketAddress(port))//使用指定的端口设置套接字地址
                    .childHandler(new ChannelInitializer<SocketChannel>() {//添加一个EchoServerHandler到子Channel的ChannelPipeline
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();//异步地绑定服务器；调用sync()方法阻塞等待知道绑定完成
            future.channel().closeFuture().sync();//获取Channel的CloseFuture,并且阻塞当前线程直到它完成
        } finally {
            group.shutdownGracefully().sync();//关闭NioEventLoopGroup,释放所有资源
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(Constants.PORT).start();
    }

}
