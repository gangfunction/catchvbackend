package com.catchvbackend.service.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {
    private final ApplicationEventPublisher publisher;
    public void publish(Transaction transaction){
        publisher.publishEvent(transaction);
    }
}
