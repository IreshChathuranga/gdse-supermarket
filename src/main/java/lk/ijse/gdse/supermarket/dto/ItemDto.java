package lk.ijse.gdse.supermarket.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemDto {
    private String itemId;
    private String itemName;
    private Integer quantity;
    private Double price;
}
