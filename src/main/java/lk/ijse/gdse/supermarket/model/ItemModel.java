package lk.ijse.gdse.supermarket.model;

import lk.ijse.gdse.supermarket.dto.CustomerDto;
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
    public ArrayList<ItemDto> getAllItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from item");
        ArrayList<ItemDto> itemDtos = new ArrayList<>();
        while (rst.next()){
            ItemDto itemDto =  new ItemDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
                   );
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }
    public String getNextItemId() throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = "Select customer_id from customer order by customer_id desc limit 1";
//        PreparedStatement pst = connection.prepareStatement(sql);

//        ResultSet rst = pst.executeQuery();
        ResultSet rst=CrudUtil.execute("select item_id from item order by item_id desc limit 1");
        if(rst.next()){
            String lastId = rst.getString(1); //COO1
            String subString = lastId.substring(1); // 002
            int i = Integer.parseInt(subString);//2
            int newIdIndex = i+1;//3
//            String newId = ; //C003
            return String.format("0%03d",newIdIndex);
        }
        return  "0001";

    }
    public boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        Boolean isSaved=CrudUtil.execute("insert into item values(?,?,?,?)", itemDto.getItemName(),itemDto.getItemName(),itemDto.getQuantity(),itemDto.getPrice());

        return  isSaved;
    }

}