package org.example.common;

import lombok.Data;

/**
 * @author huskyui
 */
@Data
public class MessageHeader {
    private int version = 1;

    private int opCode;

    private long streamId;
}
