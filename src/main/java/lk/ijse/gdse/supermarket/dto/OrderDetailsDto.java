package lk.ijse.gdse.supermarket.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailsDto {
    private String orderId;
    private String itemId;
    private int quantity;
    private double price;
}
