package ru.hse.restaurant.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.restaurant.data.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
