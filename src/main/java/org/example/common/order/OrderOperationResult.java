package org.example.common.order;

import lombok.Data;
import org.example.common.OperationResult;

/**
 * @author huskyui
 */
@Data
public class OrderOperationResult extends OperationResult {
    private final int tableId;
    private final String dish;
    private final boolean complete;
}
