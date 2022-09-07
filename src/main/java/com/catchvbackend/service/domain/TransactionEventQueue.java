package com.catchvbackend.service.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class TransactionEventQueue {
    private final Queue<Transaction> queue ;
    private final int queueSize;

    private TransactionEventQueue(int size){
        this.queueSize = size;
        this.queue= new LinkedBlockingQueue<>(queueSize);
    }
    public static TransactionEventQueue of(int size){
        return new TransactionEventQueue(size);
    }
    public boolean offer(Transaction transaction){
        boolean returnValue = queue.offer(transaction);
        healthCheck();
        return returnValue;
    }
    public Transaction poll(){
        if(queue.size()<=0){
            throw new IllegalStateException("No events in the queue!");
        }
        Transaction transaction = queue.poll();
        healthCheck();
        return transaction;
    }
    private int size(){
        return queue.size();
    }
    public boolean isFull(){
        return size() == queueSize;
    }
    public boolean isRemaining(){
        return size()>0;
    }
    private void healthCheck(){
        log.info("{\"totalQueueSize\":{},\"curruntQueueSize\":{}}",queueSize, size());
    }

}
