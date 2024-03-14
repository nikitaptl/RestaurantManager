package ru.hse.restaurant.data.entities;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "ordering")
public class Ordering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    @ElementCollection
    private List<Integer> dishId;

    private BigDecimal totalPrice;

    private boolean isActive;

    public Ordering(int id, String username, List<Integer> dishId, BigDecimal totalPrice, boolean isActive) {
        this.id = id;
        this.username = username;
        this.dishId = dishId;
        this.totalPrice = totalPrice;
        this.isActive = isActive;
    }

    public Ordering() {
        this.id = 0;
        this.username = "";
        this.dishId = null;
        this.totalPrice = BigDecimal.valueOf(0);
        this.isActive = false;
    }
}