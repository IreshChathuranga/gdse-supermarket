package lk.ijse.gdse.supermarket.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemTM {
    private String itemId;
    private String itemName;
    private Integer quantity;
    private Double price;
}
