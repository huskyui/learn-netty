package org.example.common;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.example.util.JsonUtil;

import java.nio.charset.Charset;

/**
 * @author huskyui
 */
@Data
public abstract class Message<T extends MessageBody> {
    private MessageHeader messageHeader;
    private T messageBody;

    public T getMessageBody() {
        return messageBody;
    }

    public abstract Class<T> getMessageBodyDecodeClass(int opcode);

    public void encode(ByteBuf byteBuf){
        byteBuf.writeInt(messageHeader.getVersion());
        byteBuf.writeLong(messageHeader.getStreamId());
        byteBuf.writeInt(messageHeader.getOpCode());
        byteBuf.writeBytes(JsonUtil.toJson(messageBody).getBytes());
    }

    public void decode(ByteBuf msg){
        int version = msg.readInt();
        long streamId = msg.readLong();
        int opCode = msg.readInt();

        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setVersion(version);
        messageHeader.setOpCode(opCode);
        messageHeader.setStreamId(streamId);
        this.messageHeader = messageHeader;

        Class<T> bodyClazz = getMessageBodyDecodeClass(opCode);
        T body = JsonUtil.fromJson(msg.toString(Charset.forName("UTF-8")),bodyClazz);
        this.messageBody = body;
    }
}
