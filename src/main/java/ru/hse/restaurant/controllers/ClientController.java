package ru.hse.restaurant.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.restaurant.data.entities.User;
import ru.hse.restaurant.data.repositories.DishRepository;
import ru.hse.restaurant.data.repositories.OrderingRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ClientController {
    @GetMapping("/createOrder")
    String createOrder(
            @RequestParam List<Integer> dishId
    );

    @GetMapping("/payOrder")
    String payOrder(
    );
}

@RestController
@RequestMapping("/client")
class ClientControllerImpl implements ClientController {
    private final org.slf4j.Logger Log = LoggerFactory.getLogger(ControllerImpl.class);
    @Autowired
    DishRepository dishRepository;
    @Autowired
    OrderingRepository orderingRepository;

    @PersistenceContext
    private EntityManager entityManager;
    public User currentClient;
}
