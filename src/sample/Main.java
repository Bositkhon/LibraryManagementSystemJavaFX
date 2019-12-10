package sample;

import helpers.Db;
import helpers.Md5Converter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;

import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("./../layouts/admin.fxml"));
//        primaryStage.setTitle("Library Management System");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();

        primaryStage = FXMLLoader.load(getClass().getResource("./../layouts/create_user_layout.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.show();
        User user = new User();
        System.out.println(user.isNewRecord());
        user.setUsername("Bositkhon");
        user.setPassword("bosit4me");
        user.setRoleID(1);
        if(user.save()){
            System.out.println("User has been added");
        }else{
            System.out.println("Something wrong");
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
