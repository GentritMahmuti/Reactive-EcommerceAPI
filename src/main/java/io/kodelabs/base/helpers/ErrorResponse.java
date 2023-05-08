package io.kodelabs.base.helpers;

public class ErrorResponse {
  private final String message;
  private int statusCode;

  public ErrorResponse(String message) {
    this.message = message;
  }

  public ErrorResponse(String message, int statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public String getStatusCode() {
    return message;
  }
}
