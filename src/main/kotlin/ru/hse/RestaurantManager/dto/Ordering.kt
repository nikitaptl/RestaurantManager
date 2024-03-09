package ru.hse.RestaurantManager.dto

import lombok.Builder
import lombok.Data
import java.util.UUID

@Builder
@Data
public class Ordering (
    val id : UUID,
    val name : String
)