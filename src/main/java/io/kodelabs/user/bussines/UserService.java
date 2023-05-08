package io.kodelabs.user.bussines;

import io.kodelabs.base.exceptions.NotAllowedActionException;
import io.kodelabs.base.exceptions.NotFoundException;
import io.kodelabs.user.entities.Dto.UserDto;
import io.kodelabs.user.entities.SuperAdmin;
import io.kodelabs.user.entities.User;
import io.kodelabs.user.repo.UserRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class UserService {

  @Inject UserRepository repository;

  public Uni<List<User>> getAllUsers() {
    return repository
        .getAllUsers()
        .onItem()
        .ifNull()
        .failWith(new Exception("User list is empty!"));
  }

  /**
   * NotFoundException extends Exception on failure if instance of NotFoundException recover with
   * custom message else throw
   *
   */
  public Uni<User> getUserById(int id) {
    return repository
        .getUserById(id)
        .onFailure(throwable -> throwable instanceof NotFoundException)
        .recoverWithUni(() -> Uni.createFrom().failure(new NotFoundException("User not found")));

  }

  public Uni<User> createUser(UserDto userDto) {
    User user = User.getUserFromCreateUserDto(userDto);
    return repository.createUser(user);
  }

  public Uni<User> updateUser(int id, UserDto userDto) {
    User user = User.getUserFromUpdateUserDto(id, userDto);
    return repository
        .updateUser(id, user)
        .onFailure(throwable -> throwable instanceof NotFoundException)
        .recoverWithUni(
            () ->
                Uni.createFrom()
                    .failure(new NotFoundException("A user with this Id doesn't exist!!")))
        .onFailure()
        .recoverWithUni(throwable -> Uni.createFrom().failure(throwable));
  }

  /**
   * send as query param current user id, allow to delete if current user is SuperAdmin, throw
   * NotAllowedActionException
   */
  //Test Commit
  public Uni<Void> deleteUser(int id, int currentUserId) {

    return getUserById(currentUserId)
        .onItem()
        .transformToUni(
            user -> {
              if(user instanceof SuperAdmin) {
                return repository
                    .deleteUser(id)
                    .onFailure(throwable -> throwable instanceof NotFoundException)
                    .recoverWithUni(
                        () ->
                            Uni.createFrom()
                                .failure(
                                    new NotFoundException("User with this Id doesn't exists!")));
              } else {
                return Uni.createFrom()
                    .failure(new NotAllowedActionException("You are not allowed to delete users!"));
              }
            })
        .replaceWithVoid();
  }
}
