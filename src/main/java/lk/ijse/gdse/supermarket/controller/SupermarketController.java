package lk.ijse.gdse.supermarket.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SupermarketController implements Initializable {

    public ImageView image;
    @FXML
    private AnchorPane anchorPane2;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnItem;

    @FXML
    private Button btnOrders;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/CustomerView.fxml");
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
//        anchorPane2.getChildren().clear();
//        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
//        anchorPane2.getChildren().add(load);
        navigateTo("/view/CustomerView.fxml");
    }

    @FXML
    void btnItemOnAction(ActionEvent event) throws IOException {
//        anchorPane2.getChildren().clear();
//        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Item.fxml"));
//        anchorPane2.getChildren().add(load);
        navigateTo("/view/ItemView.fxml");
    }

    @FXML
    void btnOrdersOnAction(ActionEvent event) throws IOException {
//        anchorPane2.getChildren().clear();
//        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Orders.fxml"));
//        anchorPane2.getChildren().add(load);
        navigateTo("/view/OrdersView.fxml");
    }
    public void navigateTo(String fxmlPath)  {
        try {
            anchorPane2.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            anchorPane2.getChildren().add(load);
        }catch (IOException e){
            new Alert(Alert.AlertType.ERROR,"Fail ui").show();
        }
    }


}
