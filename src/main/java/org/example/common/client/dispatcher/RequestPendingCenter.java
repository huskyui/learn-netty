package org.example.common.client.dispatcher;

import org.example.common.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huskyui
 */
public class RequestPendingCenter {
    private Map<Long,OperationResultFuture> map = new ConcurrentHashMap<>();

    public void add(Long streamId,OperationResultFuture future){
        this.map.put(streamId,future);
    }

    public void set(Long streamId, OperationResult result){
        OperationResultFuture operationResultFuture = this.map.get(streamId);
        if(operationResultFuture != null){
            operationResultFuture.setSuccess(result);
            this.map.remove(streamId);
        }
    }
}
