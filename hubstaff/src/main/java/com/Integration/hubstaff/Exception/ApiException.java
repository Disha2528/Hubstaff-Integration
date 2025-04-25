package com.Integration.hubstaff.Exception;

public class ApiException extends RuntimeException {
  private int code;

  public ApiException(String message, int code, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
