package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;


public class Main extends Application {
    public static App app = new App();
    private Stage window;
    private Scene scene1, scene2;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("./../layouts/admin_layout.fxml"));
//        primaryStage.setTitle("Library Management System");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
        window = primaryStage;
        AnchorPane parent = FXMLLoader.load(getClass().getResource("./../layouts/login_layout.fxml"));
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
        /*TextField usernameTextField = (TextField) parent.lookup("#usernameTextField");
        Button button = (Button) parent.lookup("#submitButton");
        button.setOnAction(event -> System.out.println(usernameTextField.getText()));*/
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("./../layouts/admin_layout.fxml"));
        primaryStage = FXMLLoader.load(getClass().getResource("./../layouts/login_layout.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
