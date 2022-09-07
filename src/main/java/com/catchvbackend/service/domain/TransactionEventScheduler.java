//package com.catchvbackend.service.domain;
//
//import com.catchvbackend.service.SeviceRepository.dao.FaceDataDaoJDBC;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class TransactionEventScheduler {
//    private final TransactionEventQueue eventQueue;
//    private final FaceDataDaoJDBC repository;
//
//    @Async("taskScheduler")
//    public void schedule(){
//        new TransactionEventWorker(eventQueue, repository)
//                .run();
//    }
//}
