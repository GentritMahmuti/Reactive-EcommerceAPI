package io.kodelabs.user.entities;
import io.kodelabs.user.entities.Dto.UserDto;
import io.kodelabs.user.entities.Enums.Role;

public class User {

  private static int i = 1;
  private int id;
  private String username;
  private String email;
  private int age;
  private Role role;

  /**
   * add role as enum= SimpleUser, SuperAdmin create a class SuperAdmin that extends user and by
   * default is assigned with role SuperAdmin
   */
  public User() {
    this.id = i++;
  }

  public User(String username, String email, int age, Role role) {
    this.id = i++;
    setUsername(username);
    setEmail(email);
    setAge(age);
    setRole(role);
  }

  public void setRole(Role role) {
    if (role != Role.SimpleUser && role != Role.SuperAdmin) {
      throw new IllegalArgumentException("Invalid role");
    }
    this.role = role;
  }

  public Role getRole() {
    return role;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    if (username == null || username.isEmpty()) {
      throw new IllegalArgumentException("Username cannot be null or empty");
    } else if (username.length() < 6) {
      throw new IllegalArgumentException("Username must be at least 6 characters");
    } else {
      this.username = username;
    }
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    String regex = "^[\\w-_.+]*[\\w-_.]@[\\w]+([.][\\w]+)+$";
    if (!email.matches(regex)) {
      throw new IllegalArgumentException("Invalid email format");
    } else {
      this.email = email;
    }
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    if (age < 1) {
      throw new IllegalArgumentException("Age must be positive number");
    }
    this.age = age;
  }

  public static User getUserFromUpdateUserDto(int id, UserDto userDto) {
    User userToUpdate = getUserFromCreateUserDto(userDto);
    userToUpdate.setId(id);
    return userToUpdate;
  }

  public static User getUserFromCreateUserDto(UserDto userDto) {
    User user = null;
    if (userDto.getRole() == Role.SimpleUser) {
      user =
              new User(userDto.getUsername(), userDto.getEmail(), userDto.getAge(), userDto.getRole());
    } else {
      user = new SuperAdmin(userDto.getUsername(), userDto.getEmail(), userDto.getAge());
    }
    return user;
  }
}
