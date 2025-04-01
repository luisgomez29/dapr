package co.com.bancolombia.api.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class OrderDTO {

    @NotNull(message = "{constraint.not_null}")
    @Size(min = 1, max = 4, message = "{constraint.size}")
    private String id;

    @NotNull(message = "{constraint.not_null}")
    private int quantity;

    @NotNull(message = "{constraint.not_null}")
    private String productName;

    @NotNull(message = "{constraint.not_null}")
    private double price;

}
