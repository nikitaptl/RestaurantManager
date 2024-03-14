package ru.hse.restaurant.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.restaurant.data.entities.Dish;
import ru.hse.restaurant.data.entities.Ordering;
import ru.hse.restaurant.data.entities.User;
import ru.hse.restaurant.data.repositories.DishRepository;
import ru.hse.restaurant.data.repositories.OrderingRepository;
import ru.hse.restaurant.services.CookingService;
import ru.hse.restaurant.services.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface ClientController {
    @GetMapping("/createOrder")
    String createOrder(
            @RequestParam List<Integer> dishId
    );

    @GetMapping("/expandOrder")
    String expandOrder(
            @RequestParam List<Integer> dishId
    );

    @GetMapping("/cancelOrder")
    String cancelOrder(
    );

    @GetMapping("/payOrder")
    String payOrder(
    );

    @GetMapping("/currentIncome")
    String currentIncome(
    );

    @GetMapping("/currentOrder")
    String currentOrder(
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
    @Autowired
    CookingServiceImpl kitchen;

    @PersistenceContext
    private EntityManager entityManager;
    public User currentClient;
    public Ordering currentOrdering;
    public boolean isPaid = false;
    public BigDecimal totalIncome = BigDecimal.valueOf(0);

    @Override
    public String createOrder(List<Integer> dishId) {
        if (currentClient == null) {
            return "В системе нет авторизованного клиента!";
        }
        if (kitchen.currentOrdering != null) {
            return "На кухне уже готовится заказ";
        }
        isPaid = false;
        currentOrdering = null;

        Ordering ordering = new Ordering(0, currentClient.getUsername(), dishId, BigDecimal.valueOf(0), true);
        kitchen.currentOrdering = ordering;

        StringBuilder result = new StringBuilder(kitchen.serveOrder());
        ordering = kitchen.currentOrdering;
        currentOrdering = ordering;
        kitchen.currentOrdering = null;

        if (!kitchen.isCanceled) {
            orderingRepository.save(ordering);
            result.append("<br>Итоговая стоимость заказа составила " + ordering.getTotalPrice() + " рублей. Пожалуйста, оплатите заказ");
        }
        return result.toString();
        // <br>
    }

    @Override
    public String expandOrder(List<Integer> dishId) {
        if (currentClient == null) {
            return "В системе нет авторизованного клиента!";
        }
        if (kitchen.currentOrdering == null) {
            return "На кухне нет заказов!";
        }
        return kitchen.expandOrder(dishId);
    }

    @Override
    public String cancelOrder() {
        if (currentClient == null) {
            return "В системе нет авторизованного клиента!";
        }
        if (kitchen.currentOrdering == null) {
            return "На кухне нет заказов!";
        }
        kitchen.cancelOrder();
        kitchen.currentOrdering = null;
        currentOrdering = null;
        return "Заказ был отменён!";
    }

    @Override
    public String payOrder() {
        if (currentClient == null) {
            return "В системе нет авторизованного клиента!";
        }
        if (currentOrdering == null) {
            return "Нет заказа, который можно было бы оплатить";
        }
        if (isPaid) {
            return "Последний выполненный заказ уже был оплачен";
        }
        totalIncome.add(currentOrdering.getTotalPrice());
        return "Спасибо за оплату!";
    }

    @Override
    public String currentIncome() {
        return String.format("Текущая выручка ресторана составляет %s", totalIncome.toString());
    }

    @Override
    public String currentOrder() {
        if (currentClient == null) {
            return "В системе нет авторизованного клиента!";
        }
        if (kitchen.currentOrdering == null) {
            if (currentOrdering != null) {
                return String.format("Заказ для %s готов", currentOrdering.getUsername());
            }
            return "На кухне нет заказов!";
        }
        return String.format("Заказ для %s сейчас готовится.", kitchen.currentOrdering.getUsername());
    }
}
