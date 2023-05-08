package io.kodelabs.user.controllers;

import io.kodelabs.user.bussines.UserService;
import io.kodelabs.user.entities.Dto.UserDto;
import io.kodelabs.user.entities.User;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
public class UserResource {
  @Inject UserService service;

  @GET
  public Uni<List<User>> getAllUsers() {
    return service.getAllUsers();
  }

  @GET
  @Path("/{id}")
  public Uni<User> getById(@PathParam("id") int id) {
    return service.getUserById(id);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<User> create(UserDto userDto) {
    return service.createUser(userDto);
  }

  @PUT
  @Path("{id}")
  public Uni<User> update(@PathParam("id") int id, UserDto userDto) {
    return service.updateUser(id, userDto);
  }

  @DELETE
  @Path("{id}")
  @Produces(MediaType.TEXT_PLAIN)
  /** send as query param current user id, allow delete if current user is SuperAdmin */
  public Uni<Void> delete(@PathParam("id") int id, @QueryParam("currentUserId") int currentUserId) {
    return service.deleteUser(id,currentUserId);
  }
}
