package lk.ijse.gdse.supermarket.model;

import lk.ijse.gdse.supermarket.dto.OrderDetailsDto;
import lk.ijse.gdse.supermarket.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsModel {
    private final ItemModel itemModel = new ItemModel();
    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDto> orderDetailsDTOS) throws SQLException, ClassNotFoundException {
        // Iterate through each order detail in the list
        for (OrderDetailsDto orderDetailsDTO : orderDetailsDTOS) {
            // @isOrderDetailsSaved: Saves the individual order detail
            boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDTO);
            if (!isOrderDetailsSaved) {
                // Return false if saving any order detail fails
                return false;
            }

            // @isItemUpdated: Updates the item quantity in the stock for the corresponding order detail
            boolean isItemUpdated = itemModel.reduceQty(orderDetailsDTO);
            if (!isItemUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }
        }
        // Return true if all order details are saved and item quantities updated successfully
        return true;
    }
    private boolean saveOrderDetail(OrderDetailsDto orderDetailsDto) throws SQLException, ClassNotFoundException {
        // Executes an insert query to save the order detail into the database
        return CrudUtil.execute(
                "insert into orderdetails values (?,?,?,?)",
                orderDetailsDto.getOrderId(),
                orderDetailsDto.getItemId(),
                orderDetailsDto.getQuantity(),
                orderDetailsDto.getPrice()
        );
    }
}
