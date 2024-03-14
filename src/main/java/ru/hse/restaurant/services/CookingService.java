package ru.hse.restaurant.services;

import org.springframework.stereotype.Service;
import ru.hse.restaurant.data.entities.Dish;

import java.util.List;

@Service
public interface CookingService {

    String serveOrder();

    String expandOrder(List<Integer> dishes);

    void cancelOrder();
}



