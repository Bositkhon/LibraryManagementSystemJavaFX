package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.BookModel;
import models.UserModel;
import sample.Main;

import java.awt.*;
import java.awt.print.PrinterAbortException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements LogoutInterface, Initializable {

    @FXML
    private Button homeButton, administratorsButton, studentsButton, librariansButton, booksButton, profileButton, logoutButton;

    @FXML
    private AnchorPane homePanel, administratorsPanel, studentsPanel, librariansPanel, booksPanel, profilePanel;

    @FXML
    private Label totalBooksLabel, totalUsersLabel, userName, userRole;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            BookModel bookModel = new BookModel();
            UserModel userModel = new UserModel();
            totalUsersLabel.setText(String.valueOf(userModel.getAll().size()));
            totalBooksLabel.setText(String.valueOf(bookModel.getAll().size()));
//            userName.setText(String.valueOf(user.getUsername()));
//            userRole.setText(String.valueOf(user.getRole()));
            userName.setText(Main.app.getLoggedUser().getUsername());
            userRole.setText(Main.app.getLoggedUser().getRole().getTitle());
        }catch (SQLException e){
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

    public void handlePanels(ActionEvent event) {
        if(event.getSource() == homeButton){
            homePanel.toFront();
        }
        if(event.getSource() == administratorsButton){
            administratorsPanel.toFront();
        }
        if(event.getSource() == studentsButton){
            studentsPanel.toFront();
        }
        if(event.getSource() == librariansButton){
            librariansPanel.toFront();
        }
        if(event.getSource() == booksButton){
            booksPanel.toFront();
        }
        if(event.getSource() == profileButton){
            profilePanel.toFront();
        }
    }

}
