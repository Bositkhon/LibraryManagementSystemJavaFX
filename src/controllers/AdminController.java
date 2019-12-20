package controllers;

import entities.Role;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.BookModel;
import models.RoleModel;
import models.UserModel;
import sample.Main;

import java.awt.*;
import java.awt.print.PrinterAbortException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class AdminController implements LogoutInterface, Initializable {

    @FXML
    private Button homeButton, usersButton, profileButton, logoutButton, addUserButton, addRoleButton, rolesButton;

    @FXML
    private AnchorPane homePanel, profilePanel, usersPanel, rolesPanel;

    @FXML
    private Label totalBooksLabel, totalUsersLabel;

    @FXML
    private TableView<Role> rolesTableView;

    @FXML
    private TableColumn<Role, Integer> roleSerialColumn;

    @FXML
    private TableColumn<Role, String> roleTitleColumn;

    @FXML
    private TableColumn<Role, String> roleCreatedAtColumn;

    @FXML
    private ObservableList<Role> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homePanel.toFront();
        try {
            BookModel bookModel = new BookModel();
            UserModel userModel = new UserModel();
            totalUsersLabel.setText(String.valueOf(userModel.getAll().size()));
            totalBooksLabel.setText(String.valueOf(bookModel.getAll().size()));
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            RoleModel roleModel = new RoleModel();
            this.observableList = FXCollections.observableArrayList(roleModel.getAll());
            System.out.println(this.observableList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        roleSerialColumn.setCellValueFactory(
                new PropertyValueFactory<Role, Integer>("id")
        );

        roleTitleColumn.setCellValueFactory(
                new PropertyValueFactory<Role, String >("title")
        );

        roleCreatedAtColumn.setCellValueFactory(timestamp -> {
            SimpleStringProperty property = new SimpleStringProperty();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
            property.setValue(dateFormat.format(timestamp.getValue().getCreatedAt()));
            return property;
        });

        rolesTableView.setItems(this.observableList);

    }

    @Override
    public void logout(ActionEvent event) throws IOException {
        if(Main.app.isLoggedIn()){
            Main.app.logout();
            Stage stage = (Stage)((Parent)event.getSource()).getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("./../layouts/login_layout.fxml"));
            stage.setScene(new Scene(parent));
            stage.show();
        }
    }

    public void handlePanels(ActionEvent event) {
        if(event.getSource() == homeButton){
            homePanel.toFront();
        }
        if(event.getSource() == usersButton){
            usersPanel.toFront();
        }
        if(event.getSource() == profileButton){
            profilePanel.toFront();
        }
        if(event.getSource() == rolesButton){
            rolesPanel.toFront();
        }
    }

    public void showAddUserDialogBox(ActionEvent event) throws IOException{
        Stage users_stage = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("./../layouts/add_users_layout.fxml"));
        users_stage.setScene(new Scene(parent));
        users_stage.show();
    }

    public void showAddRoleDialogBox(ActionEvent event) throws IOException {
        Stage roles_stage = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("./../layouts/add_role_layout.fxml"));
        roles_stage.setScene(new Scene(parent));
        roles_stage.show();
    }

}
