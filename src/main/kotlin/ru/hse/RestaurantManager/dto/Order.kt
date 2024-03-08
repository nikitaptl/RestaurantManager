package ru.hse.RestaurantManager.dto

import lombok.Builder
import lombok.Data
import java.util.UUID

@Builder
@Data
public class Order (
    val id : UUID,
    val name : String,
    val complexity : String,
    val num : Int
)