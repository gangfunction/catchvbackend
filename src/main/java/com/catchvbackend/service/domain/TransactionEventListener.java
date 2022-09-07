//package com.catchvbackend.service.domain;
//
//import com.catchvbackend.service.SeviceRepository.dao.FaceDataDaoJDBC;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class TransactionEventListener {
//    private final TransactionEventQueue eventQueue;
//    private final FaceDataDaoJDBC repository;
//    @EventListener
//    public void onEvent(Transaction transaction) {
//        if (!transaction.isStandby()) {
//            log.info("Transaction(id:{}) status is not STANDBY", transaction.getId());
//            return;
//        }
//        while(eventQueue.isFull()){
//            if(!transaction.isQueueWait()){
//                transaction = updateStatus(transaction, Transaction.TransactionStatus.QUEUE_WAIT);
//            }
//        }
//        transaction = updateStatus(transaction, Transaction.TransactionStatus.QUEUE);
//        eventQueue.offer(transaction);
//
//    }
//    private Transaction updateStatus(Transaction transaction, Transaction.TransactionStatus status){
//        Transaction.TransactionStatus beforeStatus = transaction.getStatus();
//        Transaction updatedTransaction = transaction.update(status);
//        log.info("{\"transactionId\": {}, \"before\":\"{}\", \"after\":\"{}\"}", transaction.getId(), beforeStatus, status);
//        return updatedTransaction;
//    }
//}
