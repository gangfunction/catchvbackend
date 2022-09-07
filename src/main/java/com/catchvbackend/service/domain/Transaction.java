package com.catchvbackend.service.domain;


import lombok.Value;

@Value(staticConstructor ="of")
public class Transaction {
    public enum TransactionStatus {
        STANDBY,
        QUEUE_WAIT,
        QUEUE,
        PROGRESS,
        SUCCESS,
        FAILURE
    }

    Long id;
    TransactionStatus status;

    public static Transaction create() {
        return Transaction.of(null, TransactionStatus.STANDBY);
    }

    public Transaction update(TransactionStatus status) {
        return Transaction.of(id, status);
    }

    public boolean isStandby() {
        return status == TransactionStatus.STANDBY;
    }

    public boolean isQueueWait() {
        return status == TransactionStatus.QUEUE_WAIT;
    }
}