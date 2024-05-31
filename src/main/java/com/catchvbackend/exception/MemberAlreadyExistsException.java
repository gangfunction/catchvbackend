package com.catchvbackend.exception;

public class MemberAlreadyExistsException extends RuntimeException {
  public MemberAlreadyExistsException(String message) {
    super(message);
  }
}

