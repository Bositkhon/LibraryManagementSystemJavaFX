package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LibrarianController implements Initializable, LogoutInterface {

    @FXML
    public Tab reservationsTab;
    @FXML
    private Tab studentsTab;

    @FXML
    private Tab booksTab;

    @FXML
    private Tab issuedBooksTab;

    @FXML
    private Tab finesTab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Parent issuedBookPane = FXMLLoader.load(getClass().getResource("./../layouts/librarian/issued_books_layout.fxml"));
            issuedBooksTab.setContent(issuedBookPane);
            Parent booksTabPane = FXMLLoader.load(getClass().getResource("./../layouts/librarian/books_layout.fxml"));
            booksTab.setContent(booksTabPane);
            Parent studentsTabPane = FXMLLoader.load(getClass().getResource("../layouts/librarian/students_layout.fxml"));
            studentsTab.setContent(studentsTabPane);
            Parent finesTabPane = FXMLLoader.load(getClass().getResource("./../layouts/librarian/fined_books_layout.fxml"));
            finesTab.setContent(finesTabPane);
            Parent reservationsTabPane = FXMLLoader.load(getClass().getResource("./../layouts/librarian/reservations_layout.fxml"));
            reservationsTab.setContent(reservationsTabPane);
        }catch (IOException e){
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