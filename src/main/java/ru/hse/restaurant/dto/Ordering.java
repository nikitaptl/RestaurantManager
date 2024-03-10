package ru.hse.restaurant.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Ordering {
    private final UUID id;
    private final String name;
}