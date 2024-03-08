package ru.hse.RestaurantManager

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestaurantManagerApplication

fun main(args: Array<String>) {
	SpringApplication.run(RestaurantManagerApplication::class.java, *args)
}
