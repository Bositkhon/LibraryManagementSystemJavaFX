package controllers.librarian;

import entities.User;
import helpers.AlertBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.UserModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyUserFormController implements Initializable {

    private User user;
    private TableView<User> userTableView;
    private Integer index;
    private boolean isInitialized = false;

    public TextField usernameTextField;
    public PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            if(!isInitialized){
                AlertBox.error("User is not initialized");
            }else{
                usernameTextField.setText(this.user.getUsername());
                System.out.println(this.user);
                System.out.println(this.userTableView);
                System.out.println(this.index);
            }
        });
    }

    public void modify(ActionEvent event) {
        try{
            UserModel userModel = new UserModel();
            this.user.setUsername(usernameTextField.getText());
            if(!passwordField.getText().isEmpty()){
                this.user.setPassword(passwordField.getText());
            }
            if(userModel.update(this.user)){
                this.userTableView.getItems().set(this.index, this.user);
                AlertBox.success("User has successfully been updated");
                ((Stage)((Parent)event.getSource()).getScene().getWindow()).close();
            }else{
                AlertBox.error("Something is wrong", user.getErrors().toString());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void set(User user, TableView<User> tableView, Integer index){
        this.user = user;
        this.userTableView = tableView;
        this.index = index;
        this.isInitialized = true;
    }

}
