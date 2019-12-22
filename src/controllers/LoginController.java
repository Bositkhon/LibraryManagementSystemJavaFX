package controllers;

import entities.Role;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.RoleModel;
import models.UserModel;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController{

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
        User user = new User();

        user.setUsername(usernameTextField.getText());
        user.setPassword(passwordField.getText());

        if (user.validate()) {
            User existing_user = userModel.getByUsernameAndPassword(usernameTextField.getText(), passwordField.getText());
            if (existing_user != null) {
                Main.app.login(existing_user);
                Stage window = (Stage) ((Parent) event.getSource()).getScene().getWindow();
                Parent parent = new AnchorPane();
                if (existing_user.getRole().getTitle().equals("Administrator")) {
                    parent = FXMLLoader.load(getClass().getResource("./../layouts/admin_layout.fxml"));
                } else if (existing_user.getRole().getTitle().equals("Student")) {
                    parent = FXMLLoader.load(getClass().getResource("./../layouts/student_layout.fxml"));
                } else if (existing_user.getRole().getTitle().equals("Librarian")) {
                    parent = FXMLLoader.load(getClass().getResource("./../layouts/librarian_layout.fxml"));
                }
                window.setScene(new Scene(parent));
                window.show();
            } else {
                user.addError("Username or password is wrong! Please try again!");
                errorMessagesLabel.setText(user.getErrors().toString());
            }
        }
    }
}
