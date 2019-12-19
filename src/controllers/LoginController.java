package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.UserModel;
import sample.Main;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    public Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    private Label errorMessagesLabel;

    public void login(ActionEvent event) throws IOException, SQLException {
        UserModel userModel = new UserModel();
        User user = userModel.getByUsernameAndPassword(usernameTextField.getText(), passwordField.getText());
        if(user == null){
            errorMessagesLabel.setText("No such user");
        }else{
            Main.app.login(user);
            Stage window = (Stage) ((Parent) event.getSource()).getScene().getWindow();
            Parent anchorPane = FXMLLoader.load(getClass().getResource("./../layouts/admin_layout.fxml"));
            window.setScene(new Scene(anchorPane));
        }

    }


}
