package commons.transport.channel;

import commons.transport.NettyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline()
                .addLast("decoder", new HttpRequestDecoder())
                .addLast("encoder", new HttpResponseEncoder())
                .addLast("handler", new NettyServerHandler());
    }
}