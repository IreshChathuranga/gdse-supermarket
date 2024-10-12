package lk.ijse.gdse.supermarket.model;

import lk.ijse.gdse.supermarket.dto.ItemDto;
import lk.ijse.gdse.supermarket.dto.OrderDetailsDto;
import lk.ijse.gdse.supermarket.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class ItemModel {
    public ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select item_id from item");

        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()){
            itemIds.add(rst.getString(1));
        }

        return itemIds;
    }
    public ItemDto findById(String selectedItemId) throws SQLException, ClassNotFoundException {
        // Execute SQL query to find the item by ID
        ResultSet rst = CrudUtil.execute("select * from item where item_id=?", selectedItemId);

        // If the item is found, create an ItemDTO object with the retrieved data
        if (rst.next()) {
            return new ItemDto(
                    rst.getString(1),  // Item ID
                    rst.getString(2),  // Item Name
                    rst.getInt(3),     // Item Quantity
                    rst.getDouble(4)   // Item Price
            );
        }

        // Return null if the item is not found
        return null;
    }
    public boolean reduceQty(OrderDetailsDto orderDetailsDto) throws SQLException, ClassNotFoundException {
        // Execute SQL query to update the item quantity in the database
        return CrudUtil.execute(
                "update item set quantity = quantity - ? where item_id = ?",
                orderDetailsDto.getQuantity(),   // Quantity to reduce
                orderDetailsDto.getItemId()      // Item ID
        );
    }
}