package ru.hse.restaurant.controllers;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.restaurant.dto.Ordering;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/orders")
@Slf4j
public class RestaurantController {


    private final org.slf4j.Logger Log = LoggerFactory.getLogger(RestaurantController.class);

    @GetMapping(path = "/{id}")
    @ResponseBody
    public Ordering getOrder(@PathVariable UUID id) {
        Optional<Ordering> optionalOrderDbModel;

        return Ordering.builder().id(id).name("hi").build();
    }

    @GetMapping
    @ResponseBody
    public List<Ordering> getOrders() {
        List<Ordering> returnValue = List.of();

        return returnValue;
    }
}
