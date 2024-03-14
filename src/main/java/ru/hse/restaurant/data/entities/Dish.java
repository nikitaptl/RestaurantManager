package ru.hse.restaurant.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Data
@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    // Для невозможности создания отрицательной цены, используется BigDecimal
    private BigDecimal price;

    private int minutesToCook;

    public Dish() {
        this.id = 0;
        price = BigDecimal.valueOf(0);
        name = "";
        minutesToCook = 0;
    }

    public Dish(int id, String name, BigDecimal price, int minutesToCook) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.minutesToCook = minutesToCook;
    }

    public String ToString() {
        return id + ") Блюдо " + name + ", цена = " + price + ", время приготовления = " + minutesToCook + " минут";
    }
}
