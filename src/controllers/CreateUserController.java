package controllers;

import entities.Role;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.RoleModel;
import models.UserModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {

    @FXML
    private Label errorMessageLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> rolesChoiceBox;

    @FXML
    private Button createButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            RoleModel roleModel = new RoleModel();
            List<Role> roles = roleModel.getAll();
            for(Role role : roles){
                rolesChoiceBox.getItems().add(role.getTitle());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUser(ActionEvent event) throws SQLException {
        UserModel userModel = new UserModel();
        User user = new User();
        RoleModel roleModel = new RoleModel();
        user.setUsername(usernameTextField.getText());
        user.setPassword(passwordField.getText());
        Role role = roleModel.getByTitle(rolesChoiceBox.getSelectionModel().getSelectedItem());
        user.setRoleId(role.getId());
        if(userModel.insert(user)){
            Stage currentStage = (Stage) ((Parent)event.getSource()).getScene().getWindow();
            currentStage.close();
        }else{
            errorMessageLabel.setText(user.getErrors().toString());
        }
    }
}
