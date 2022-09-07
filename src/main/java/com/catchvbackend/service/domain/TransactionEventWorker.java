package com.catchvbackend.service.domain;

import com.catchvbackend.service.SeviceRepository.dao.FaceDataDaoJDBC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
@Slf4j
@RequiredArgsConstructor
public class TransactionEventWorker implements Runnable{
    private final TransactionEventQueue eventQueue;
    private final FaceDataDaoJDBC repository;

    @Override
    @Transactional
    public void run() {
     if (eventQueue.isRemaining()){
         Transaction transaction= eventQueue.poll();
         try{
             processing(1_000);
             successOrFailure(transaction);
         }catch(Exception e){
             handlingInCaseOfFailure(transaction);
             log.error(e.getMessage());
         }
     }
    }
    private void processing(int processingTimeInMillis){
        try{
            Thread.sleep(processingTimeInMillis);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    private void successOrFailure(Transaction transaction) {
        if (Math.random() < 0.5) {
            updateStatus(transaction, Transaction.TransactionStatus.SUCCESS);
        } else {
            updateStatus(transaction, Transaction.TransactionStatus.FAILURE);
        }
    }
    private void handlingInCaseOfFailure(Transaction transaction) {
        updateStatus(transaction, Transaction.TransactionStatus.FAILURE);
    }
    private Transaction updateStatus(Transaction transaction, Transaction.TransactionStatus status) {
        Transaction.TransactionStatus beforeStatus = transaction.getStatus();
        Transaction updatedTransaction= transaction.update(status);
        log.info("{\"transactionId\": {}, \"before\": {}, \"after\": \"{}\"}", transaction.getId(), beforeStatus, status);
        return updatedTransaction;
    }
}
