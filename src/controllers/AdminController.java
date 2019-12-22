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
    private Tab usersTab, booksTab;

    @FXML
    private Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Main.app.isLoggedIn()){
            usernameLabel.setText(Main.app.getLoggedUser().getUsername());
        }
        try {
            Parent booksParent = FXMLLoader.load(getClass().getResource("./../layouts/administrator/books_layout.fxml"));
            booksTab.setContent(booksParent);
            Parent usersParent = FXMLLoader.load(getClass().getResource("./../layouts/administrator/users_layout.fxml"));
            usersTab.setContent(usersParent);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
