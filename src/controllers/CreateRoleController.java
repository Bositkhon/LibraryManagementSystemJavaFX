package controllers;

import entities.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.RoleModel;

import java.sql.SQLException;

public class CreateRoleController {

    @FXML
    private TextField titleTextField;

    @FXML
    private Label errorMessageLabel;

    public void createRole(ActionEvent event) throws SQLException {
        System.out.println("inCreateRole");
        RoleModel roleModel = new RoleModel();
        Role role = new Role();
        role.setTitle(titleTextField.getText());
        if(roleModel.insert(role)){
            System.out.println("Success");
            Stage current_stage = (Stage) ((Parent) event.getSource()).getScene().getWindow();
            current_stage.close();
        }else{
            errorMessageLabel.setText(role.getErrors().toString());
        }
    }

}
