package sample;

import entities.Role;
import entities.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.RoleModel;
import models.UserModel;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Main extends Application {
    public static App app = new App();

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane parent = FXMLLoader.load(getClass().getResource("./../layouts/login_layout.fxml"));
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
