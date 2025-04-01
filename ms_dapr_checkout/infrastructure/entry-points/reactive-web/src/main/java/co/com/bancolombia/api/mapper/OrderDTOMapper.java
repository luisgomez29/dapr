package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.OrderDTO;
import co.com.bancolombia.model.order.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDTOMapper {

    Order toEntity(OrderDTO genreDto);

}
