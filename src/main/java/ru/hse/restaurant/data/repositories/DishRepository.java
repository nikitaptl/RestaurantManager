package ru.hse.restaurant.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.restaurant.data.entities.Dish;

public interface DishRepository extends JpaRepository<Dish, Integer> {
}
