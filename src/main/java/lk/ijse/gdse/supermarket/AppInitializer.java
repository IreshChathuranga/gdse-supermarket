package lk.ijse.gdse.supermarket;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

            Parent load = FXMLLoader.load(getClass().getResource("/view/Supermarket.fxml"));
            Scene scene = new Scene(load);
            stage.setScene(scene);
            stage.setTitle("Sample Application");
            stage.show();

    }
}
