package org.example.common.order;

import lombok.Data;
import org.example.common.Operation;
import org.example.common.OperationResult;

/**
 * @author huskyui
 */
@Data
public class OrderOperation extends Operation {

    private int tableId;
    private String dish;


    public OrderOperation(int tableId, String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }

    @Override
    public OperationResult execute() {
        System.out.println("order's executing startup with orderRequest: "+toString());
        // execute order logic
        System.out.println("order's executiong complete");
        OrderOperationResult orderResponse = new OrderOperationResult(tableId,dish,true);
        return orderResponse;
    }
}
