package org.example.common.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.example.common.RequestMessage;
import org.example.common.client.codec.*;
import org.example.common.client.dispatcher.OperationResultFuture;
import org.example.common.client.dispatcher.RequestPendingCenter;
import org.example.common.client.dispatcher.ResponseDispatcherHandler;
import org.example.common.order.OrderOperation;
import org.example.util.IdUtil;

import java.util.concurrent.ExecutionException;

/**
 * @author huskyui
 */
public class ClientV2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());

        RequestPendingCenter center = new RequestPendingCenter();

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolEncoder());
                pipeline.addLast(new OrderProtocolDecoder());

                pipeline.addLast(new ResponseDispatcherHandler(center));

                pipeline.addLast(new OperationToRequestMessageEncoder());


                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090);

        // 上面连接服务器端是异步，需要确定连接之后，我们再发送请求给服务器端
        channelFuture.sync();


        // 发送请求
        long streamId = IdUtil.nextId();
        OperationResultFuture future = new OperationResultFuture();
        OrderOperation operation = new OrderOperation(100,"好吃的");
        RequestMessage requestMessage = new RequestMessage(streamId,operation);
        center.add(streamId,future);
        channelFuture.channel().writeAndFlush(requestMessage);

        System.out.println(future.get());


        channelFuture.channel().closeFuture().get();


    }
}
