package commons.transport;

import commons.transport.channel.HttpChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.io.Closeable;

public class NettyServer implements Closeable {
    /**
     * netty ServerBootstrap.
     */
    private ServerBootstrap bootstrap;
    /**
     * the boss channel that receive connections and dispatch these to worker channel.
     */
    private Channel channel;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public void doOpen() {
        bootstrap = new ServerBootstrap();

        //监听客户端连接
        bossGroup = new NioEventLoopGroup(new DefaultThreadFactory("BossGroup"));
        //处理每一个连接发生的读写事件
        workerGroup = new NioEventLoopGroup(new DefaultThreadFactory("WorkerGroup"));

        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new HttpChannelInitializer());

        // bind
        ChannelFuture channelFuture = bootstrap.bind(6666);
        channelFuture.syncUninterruptibly();
        channel = channelFuture.channel();
    }

    public void close() {
        bossGroup.shutdownGracefully().syncUninterruptibly();
        workerGroup.shutdownGracefully().syncUninterruptibly();
    }
}