package lk.ijse.gdse.supermarket.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.supermarket.dto.ItemDto;
import lk.ijse.gdse.supermarket.dto.tm.ItemTM;
import lk.ijse.gdse.supermarket.model.ItemModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML
    private Button btnDeleteItem;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSaveItem;

    @FXML
    private Button btnUpdateItem;

    @FXML
    private TableColumn<ItemTM,String> colItemId;

    @FXML
    private TableColumn<ItemTM,String> colName;

    @FXML
    private TableColumn<ItemTM,Double> colPrice;

    @FXML
    private TableColumn<ItemTM,Integer> colQuantity;

    @FXML
    private Label lblItem;

    @FXML
    private Label lblItemId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblQty;

    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveItemOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String itemId = lblItem.getText();
        String itemName = txtName.getText();
        Integer quantity = Integer.valueOf(txtQuantity.getText());
        Double price = Double.valueOf(txtPrice.getText());


        ItemDto itemDto = new ItemDto(
                itemId,
                itemName,
                quantity,
                price

        );

        boolean isSaved  = itemModel.saveItem(itemDto);
        if(isSaved){
            loadNextItemId();
            txtName.setText("");
            txtQuantity.setText("");
            txtPrice.setText("");
            new Alert(Alert.AlertType.INFORMATION,"Item Saved").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Save fail").show();

        }
    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {

    }

    @FXML
    void onClickTable(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        try{
            loadNextItemId();
            loadTableData();
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail Item id").show();
        }
    }
    ItemModel itemModel = new ItemModel();
    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDto> itemDtos = itemModel.getAllItem();
        ObservableList<ItemTM> itemTMS = FXCollections.observableArrayList();
        for(ItemDto itemDto:itemDtos){
            ItemTM itemTM=new ItemTM();
            itemTM.setItemId(itemDto.getItemId());
            itemTM.setItemName(itemDto.getItemName());
            itemTM.setQuantity(itemTM.getQuantity());
            itemTM.setPrice(itemTM.getPrice());
            itemTMS.add(itemTM);
        }
        tblItem.setItems(itemTMS);
    }
    public void loadNextItemId() throws SQLException, ClassNotFoundException {
        String nextItemId = itemModel.getNextItemId();
        lblItem.setText(nextItemId);
    }
}
