package lk.ijse.gdse.supermarket.model;

import lk.ijse.gdse.supermarket.db.DBConnection;
import lk.ijse.gdse.supermarket.dto.CustomerDto;
import lk.ijse.gdse.supermarket.dto.tm.CustomerTM;
import lk.ijse.gdse.supermarket.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public String getNextCustomerId() throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = "Select customer_id from customer order by customer_id desc limit 1";
//        PreparedStatement pst = connection.prepareStatement(sql);

//        ResultSet rst = pst.executeQuery();
        ResultSet rst=CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");
        if(rst.next()){
            String lastId = rst.getString(1); //COO1
            String subString = lastId.substring(1); // 002
            int i = Integer.parseInt(subString);//2
            int newIdIndex = i+1;//3
//            String newId = ; //C003
            return String.format("C%03d",newIdIndex);
        }
        return  "C001";

    }


    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
//        Connection connection =  DBConnection.getInstance().getConnection();
//        String sql  = "insert into customer values(?,?,?,?,?)";
//        PreparedStatement pst = connection.prepareStatement(sql);
//
//        pst.setObject(1,customerDto.getCustomerId());
//        pst.setObject(2,customerDto.getName());
//        pst.setObject(3,customerDto.getNic());
//        pst.setObject(4,customerDto.getEmail());
//        pst.setObject(5,customerDto.getPhone());
//
//        int result = pst.executeUpdate();
//        boolean isSaved = result>0;
        Boolean isSaved=CrudUtil.execute("insert into customer values(?,?,?,?,?)", customerDto.getCustomerId(),customerDto.getName(),customerDto.getNic(),customerDto.getEmail(),customerDto.getPhone());

        return  isSaved;
    }
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from customer");
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        while (rst.next()){
            CustomerDto customerDto =  new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5));
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update customer set name=?, nic=?, email=?, phone=? where customer_id=?",
                customerDto.getName(),
                customerDto.getNic(),
                customerDto.getEmail(),
                customerDto.getPhone(),
                customerDto.getCustomerId()
        );
    }
    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from customer where customer_id=?", customerId);
    }
    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()){
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }
    public CustomerDto findById(String selectedCusId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from customer where customer_id=?", selectedCusId);

        if (rst.next()) {
            return new CustomerDto(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5)   // Phone
            );
        }
        return null;
    }
}
