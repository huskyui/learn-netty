package org.example.common.client.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 解决客户端TCP粘包 半包问题
 * @author huskyui
 */
public class OrderFrameEncoder extends LengthFieldPrepender {

    public OrderFrameEncoder() {
        super(2);
    }
}
