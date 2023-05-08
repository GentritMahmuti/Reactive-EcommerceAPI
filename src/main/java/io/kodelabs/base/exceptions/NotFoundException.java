package io.kodelabs.base.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class NotFoundException extends Exception {

  public NotFoundException() {}

  public NotFoundException(String message) {
    super(message);
  }
}
