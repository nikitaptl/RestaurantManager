package ru.hse.RestaurantManager.mappers

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import ru.hse.RestaurantManager.dto.Ordering as OrderingDto
import ru.hse.RestaurantManager.data.repositories.Ordering as OrderingDb

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderDbModelToOrderDtoMapper {

    fun map(orderDbModel : OrderingDb) : OrderingDto {

    }
}