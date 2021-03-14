package org.example.common.server.codec.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.common.Operation;
import org.example.common.OperationResult;
import org.example.common.RequestMessage;
import org.example.common.ResponseMessage;

/**
 * 主要业务处理
 * 基础于SimpleChannelInboundHandler 代码中有release
 * @author huskyui
 */
public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        Operation operation = requestMessage.getMessageBody();
        // do logic operation
        OperationResult operationResult = operation.execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(requestMessage.getMessageHeader());
        responseMessage.setMessageBody(operationResult);
        ctx.writeAndFlush(responseMessage);
        System.out.println("ctx write and flush");
    }
}
