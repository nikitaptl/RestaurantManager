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

    private UUID userId;

    @ElementCollection
    private List<Integer> dishId;

    private BigDecimal totalPrice;

    private boolean isActive;
}