package com.catchvbackend.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void builder(){
        Event build = Event.builder()
                .name("Spring Api")
                .description("RESTAPI")
                .build();
        Assertions.assertThat(build).isNotNull();
    }
    @Test
    public void javaBean(){
        new Event();
    }

}