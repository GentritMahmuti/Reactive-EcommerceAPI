package io.kodelabs.user.entities;

import io.kodelabs.user.entities.Enums.Role;

public class SuperAdmin extends User {
    public SuperAdmin(String username, String email, int age) {
        super(username, email, age, Role.SuperAdmin);
    }
}
