package com.catchvbackend.api.FaceData.service;

import lombok.Getter;

@Getter
public enum QueueStatus {
    EMPTY("200"),
    PROGRESS("202"),
    FULL("404");

    private final String codes;

    QueueStatus(String number) {
        this.codes = number;
    }
}
