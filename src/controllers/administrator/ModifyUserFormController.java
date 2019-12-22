package controllers.administrator;

import entities.Role;
import entities.User;
import helpers.AlertBox;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.RoleModel;
import models.UserModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyUserFormController implements Initializable {

    private TableView<User> returnTableView;

    private Integer index;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<Role> roleChoiceBox;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            RoleModel roleModel = new RoleModel();
            try {
                this.roleChoiceBox.getItems().addAll(roleModel.getAll());
                for(Role role : this.roleChoiceBox.getItems()){
                    if(role.getTitle().equals(this.user.getRole().getTitle())){
                        this.roleChoiceBox.setValue(role);
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            this.usernameTextField.setText(this.user.getUsername());

        });
    }

    public void change(ActionEvent event) throws SQLException {
        UserModel userModel = new UserModel();
        this.user.setUsername(usernameTextField.getText());
        this.user.setPassword(passwordField.getText().isEmpty() ? this.user.getPassword() : passwordField.getText());
        this.user.setRoleId(this.roleChoiceBox.getSelectionModel().getSelectedItem().getId());
        if(userModel.update(this.user)){
            AlertBox.success("User has successfully been updated");
            ((Stage)((Parent)event.getSource()).getScene().getWindow()).close();
            getReturnTableView().getItems().set(index, user);
        }else{
            AlertBox.error("User can not be updated");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setReturnTableView(TableView<User> returnTableView, Integer index) {
        this.returnTableView = returnTableView;
        this.index = index;
    }

    public TableView<User> getReturnTableView() {
        return returnTableView;
    }
}
