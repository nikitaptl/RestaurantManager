package ru.hse.restaurant.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.hse.restaurant.dto.Ordering;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderDbModelToOrderDtoMapper {
    Ordering map(ru.hse.restaurant.data.repositories.Ordering orderDbModel);
}