package ru.hse.RestaurantManager.controllers

import lombok.Builder
import lombok.Data
import lombok.extern.slf4j.Slf4j

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import ru.hse.RestaurantManager.dto.Order
import java.util.UUID

@Data
@Builder
class MyClass(
    val id: UUID,
    val name: String
    // Другие поля класса
)
@RestController
@RequestMapping(path = arrayOf("/orders"))
@Slf4j
class RestaurantController {

    private final val log = LoggerFactory.getLogger(RestaurantController::class.java)
    @GetMapping(path = arrayOf("/{id}"))
    @ResponseBody
    public fun getOrder(@PathVariable id: UUID) : Order {
        return Order(id, "hi", "hi", 54)
    }

    @GetMapping(path = arrayOf("/orders"))
    @ResponseBody
    public fun getBooks() : List<Order>{
        return emptyList();
    }
}