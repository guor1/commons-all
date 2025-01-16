package commons.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.util.concurrent.DefaultThreadFactory;

public class Netty4j {
    public static void main(String[] args) throws InterruptedException {
        //创建boss线程组，用于接收客户端连接，设置为1个线程
        EventLoopGroup bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("BossGroup"));
        //创建worker线程组，用于处理已建立连接的IO操作，线程数默认为CPU核心数*2
        EventLoopGroup workerGroup = new NioEventLoopGroup(new DefaultThreadFactory("WorkerGroup"));
        try {
            //创建服务器启动引导类
            ServerBootstrap bootstrap = new ServerBootstrap().group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class)//指定使用NIO传输Channel
                    .option(ChannelOption.SO_BACKLOG, 128)//设置服务端接收连接的队列长度
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持连接活动状态
                    .childHandler(new ChannelInitializer<ServerSocketChannel>() {
                        @Override
                        protected void initChannel(ServerSocketChannel ch) {
                            ch.pipeline().addLast("decoder", new HttpRequestDecoder())//HTTP请求解码器
                                    .addLast("encoder", new HttpResponseEncoder())//HTTP响应编码器
                                    .addLast("handler", new NettyServerHandler());//自定义业务处理器
                        }
                    });

            //绑定端口并启动服务器
            ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            // 等待服务器Channel关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}