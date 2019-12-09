package sample;

import helpers.Db;
import helpers.Md5Converter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("./../layouts/admin.fxml"));
//        primaryStage.setTitle("Library Management System");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
//
//        primaryStage = FXMLLoader.load(getClass().getResource("./../layouts/login_layout.fxml"));
//        primaryStage.setTitle("Login");
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
