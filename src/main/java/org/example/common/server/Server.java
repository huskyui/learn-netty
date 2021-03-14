package org.example.common.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.example.common.server.codec.OrderFrameDecoder;
import org.example.common.server.codec.OrderFrameEncoder;
import org.example.common.server.codec.OrderProtocolDecoder;
import org.example.common.server.codec.OrderProtocolEncoder;
import org.example.common.server.codec.handler.OrderServerProcessHandler;

import java.util.concurrent.ExecutionException;

/**
 * @author huskyui
 */
public class Server {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.group(new NioEventLoopGroup());

        // 可能一个是handler
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));

        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                // 这边的顺序要保证是一个V字型，要保证顺序。业务逻辑放在最下层，最上层是解析bytebuf,
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolEncoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                pipeline.addLast(new OrderServerProcessHandler());
            }
        });
        ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
        channelFuture.channel().closeFuture().get();


    }
}
