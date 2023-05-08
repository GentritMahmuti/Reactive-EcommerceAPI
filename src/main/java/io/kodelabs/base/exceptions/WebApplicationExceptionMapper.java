package io.kodelabs.base.exceptions;

import io.kodelabs.base.helpers.ErrorResponse;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class WebApplicationExceptionMapper {
  @ServerExceptionMapper
  public Response toResponse(NotFoundException e) {
    int statusCode = 404;
    String message = e.getMessage();
    ErrorResponse errorResponse = new ErrorResponse(message, statusCode);
    return Response.status(statusCode).entity(errorResponse).build();
  }

}
