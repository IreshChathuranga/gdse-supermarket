package lk.ijse.gdse.supermarket.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter                 // @Getter: Automatically generates getter methods for all fields
@Setter                 // @Setter: Automatically generates setter methods for all fields
@AllArgsConstructor     // @AllArgsConstructor: Generates a constructor with all fields as parameters
@NoArgsConstructor      // @NoArgsConstructor: Generates a no-argument constructor
@ToString
public class OrderDto {
    private String orderId;
    private String customerId;
    private Date orderDate;

    // @orderDetailsDTOS: A list of OrderDetailsDTO objects, each representing an item in the order
    private ArrayList<OrderDetailsDto> orderDetailsDTOS;
}
