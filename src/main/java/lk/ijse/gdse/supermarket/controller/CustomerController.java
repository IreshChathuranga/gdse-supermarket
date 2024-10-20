package lk.ijse.gdse.supermarket.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.supermarket.db.DBConnection;
import lk.ijse.gdse.supermarket.dto.CustomerDto;
import lk.ijse.gdse.supermarket.dto.tm.CustomerTM;
import lk.ijse.gdse.supermarket.model.CustomerModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CustomerController implements Initializable {

    public Button btnSave;
    public Label lblCustomer;
    public Label lblName;
    public TextField txtCustomer;
    public Label lblNIC;
    public Label lblEmail;
    public Label lblPhone;
    public TextField txtName;
    public TextField txtNIC;
    public TextField txtEmail;
    public TextField txtPhone;
    public Label lblCust;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    @FXML
    private TableColumn<CustomerTM,String> customerEmail;

    @FXML
    private TableColumn<CustomerTM,String> customerId;

    @FXML
    private TableColumn<CustomerTM,String> customerNIC;

    @FXML
    private TableColumn<?, ?> customerNIC1;

    @FXML
    private TableColumn<CustomerTM,String> customerName;

    @FXML
    private TableColumn<CustomerTM,String> customerPhone;

    @FXML
    private TableView<CustomerTM> tblTable1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        customerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));




//        CustomerTM customerTM1 = new CustomerTM("C001","Kamal","045004005043","Kamal@gmail.com","0765645654");
//        CustomerTM customerTM2 = new CustomerTM("C002","Nimal","045004004053","Nimal@gmail.com","0775645789");
//
//        ArrayList<CustomerTM> customertmsArray = new ArrayList<>();
//        customertmsArray.add(customerTM1);
//        customertmsArray.add(customerTM2);
//
//        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();
//
//        for(CustomerTM customer : customertmsArray){
//            customerTMS.add(customer);
//        }
//        tblTable1.setItems(customerTMS);
        try{
            loadNextCustomerId();
            loadTableData();
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail Customer id").show();
        }

    }

    CustomerModel customerModel = new CustomerModel();
    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDto> customerDtos = customerModel.getAllCustomers();
        ObservableList<CustomerTM> customerTMS =FXCollections.observableArrayList();
        for(CustomerDto customerDto:customerDtos){
            CustomerTM customerTM=new CustomerTM();
            customerTM.setCustomerId(customerDto.getCustomerId());
            customerTM.setName(customerDto.getName());
            customerTM.setNic(customerDto.getNic());
            customerTM.setEmail(customerDto.getEmail());
            customerTM.setPhone(customerDto.getPhone());
            customerTMS.add(customerTM);
        }
        tblTable1.setItems(customerTMS);
    }
    public void loadNextCustomerId() throws SQLException, ClassNotFoundException {
        String nextCustomerId = customerModel.getNextCustomerId();
        lblCust.setText(nextCustomerId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = lblCust.getText();
        String name = txtName.getText();
        String nic = txtNIC.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNIC.setStyle(txtNIC.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (!isValidNic) {
            txtNIC.setStyle(txtNIC.getStyle() + ";-fx-border-color: red;");
//            return;
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidNic && isValidEmail && isValidPhone) {

            CustomerDto customerDto = new CustomerDto(
                    customerId,
                    name,
                    nic,
                    email,
                    phone
            );

            boolean isSaved = customerModel.saveCustomer(customerDto);
            if (isSaved) {
                loadNextCustomerId();
                txtName.setText("");
                txtNIC.setText("");
                txtEmail.setText("");
                txtPhone.setText("");
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save fail").show();

            }
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = lblCustomer.getText();
        String name = txtName.getText();
        String nic = txtNIC.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNIC.setStyle(txtNIC.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (!isValidNic) {
            txtNIC.setStyle(txtNIC.getStyle() + ";-fx-border-color: red;");
//            return;
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidNic && isValidEmail && isValidPhone) {
            CustomerDto customerDTO = new CustomerDto(
                    customerId,
                    name,
                    nic,
                    email,
                    phone
            );

            boolean isUpdate = customerModel.updateCustomer(customerDTO);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update customer...!").show();
            }
        }
    }
    

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = lblCustomer.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = customerModel.deleteCustomer(customerId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer...!").show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

//    private void loadTable(){
//        String columns[] = {"customerId", "", "name", "nic", "email","phone"};
//        ObservableList dtm = new observableList(columns, 0){
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//
//        };
//        try {
//            ArrayList<CustomerDto> customerDtos = customerModel.getAll();
//            for (CustomerDto customerDto : customerDtos) {
//                Object[] rowData = {customerDto.getCustomerId(), customerDto.getName(), customerDto.getNic(), customerDto.getEmail(), customerDto.getPhone()};
//                dtm.addRow(rowData);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, e.getMessage());
//        }
//
//        tblTable1.(dtm);
//    }
private void refreshPage() throws SQLException, ClassNotFoundException {
    loadNextCustomerId();
    loadTableData();

    btnSave.setDisable(false);

    btnUpdate.setDisable(true);
    btnDelete.setDisable(true);

    txtName.setText("");
    txtNIC.setText("");
    txtEmail.setText("");
    txtPhone.setText("");
}


    public void onClickTable(MouseEvent mouseEvent) {
        CustomerTM customerTM = tblTable1.getSelectionModel().getSelectedItem();
        if (customerTM != null) {
            lblCust.setText(customerTM.getCustomerId());
            txtName.setText(customerTM.getName());
            txtNIC.setText(customerTM.getNic());
            txtEmail.setText(customerTM.getEmail());
            txtPhone.setText(customerTM.getPhone());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void allReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/Customer_report.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    null,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
//           e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}


