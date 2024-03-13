package ru.hse.restaurant.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.restaurant.data.entities.Ordering;

public interface OrderingRepository extends JpaRepository<Ordering, Integer> {
}
