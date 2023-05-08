package io.kodelabs.user.repo;

import io.kodelabs.base.exceptions.NotFoundException;
import io.kodelabs.user.entities.User;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

@ApplicationScoped
public class UserRepository {

  public static HashMap<Integer, User> users = new HashMap<>();

  public Uni<List<User>> getAllUsers() {
    return Uni.createFrom().item(users.values().stream().toList());
  }

  public Uni<User> getUserById(int userId) {
    return Uni.createFrom()
        .emitter(
            emitter -> {
              User user = users.get(userId);

              if (user != null) {
                emitter.complete(user);
              } else {
                emitter.fail(new NotFoundException());
                // emitter.complete(null);
              }
            });
  }

  public Uni<User> createUser(User user) {
    return Uni.createFrom()
        .emitter(
            emitter -> {
              if (users.containsKey(user.getId())) {
                emitter.fail(new Exception("A user with this Id already exist."));
              } else {
                users.put(user.getId(), user);
                emitter.complete(user);
              }
            });
  }

  public Uni<User> updateUser(int userId, User newUser) {
    return Uni.createFrom()
        .emitter(
            emitter -> {
              if (users.containsKey(userId)) {
                users.put(newUser.getId(), newUser);
                emitter.complete(newUser);
              } else {
                emitter.fail(new NotFoundException());
              }
            });
  }

  public Uni<Void> deleteUser(int userId) {
    return Uni.createFrom()
        .emitter(
            emitter -> {
              if (users.containsKey(userId)) {
                users.remove(userId);
                emitter.complete(null);
              } else {
                emitter.fail(new NotFoundException());
              }
            });
  }
}
