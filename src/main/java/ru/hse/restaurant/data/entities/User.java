package ru.hse.restaurant.data.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String username;

    String password;

    String user_type;

    public User() {
        this.id = UUID.randomUUID();
        username = "";
        password = "";
        user_type = "";
    }

    public User(UUID uuid, String username, String password, String userType) {
        this.id = uuid;
        this.username = username;
        this.password = password;
        this.user_type = userType;
    }

    public String ToString() {
        return String.format("User %s, %s", username, user_type);
    }
}
