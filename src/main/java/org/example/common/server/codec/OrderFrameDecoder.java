package org.example.common.server.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 解决TCP中粘包，半包问题
 * @author huskyui
 */
public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {

    public OrderFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
