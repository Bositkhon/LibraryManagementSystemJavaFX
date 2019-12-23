package controllers;

import controllers.student.BooksController;
import entities.IssuedBook;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable, LogoutInterface {

    @FXML
    public Tab reservationsTab;
    @FXML
    private Tab booksTab;

    @FXML
    private Tab issuedBooksTab;

    @FXML
    private Tab finedBooksTab;

    @FXML
    private Tab networkTab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent parent1 = FXMLLoader.load(getClass().getResource("./../layouts/student/books_layout.fxml"));
            booksTab.setContent(parent1);
            Parent parent2 = FXMLLoader.load(getClass().getResource("./../layouts/student/issued_books_layout.fxml"));
            issuedBooksTab.setContent(parent2);
            Parent parent3 = FXMLLoader.load(getClass().getResource("./../layouts/student/fined_books_layout.fxml"));
            finedBooksTab.setContent(parent3);
            Parent parent4 = FXMLLoader.load(getClass().getResource("./../layouts/student/reservations_layout.fxml"));
            reservationsTab.setContent(parent4);
            Parent parent5 = FXMLLoader.load(getClass().getResource("../layouts/student/networking_layout.fxml"));
            networkTab.setContent(parent5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search(){

    }

    @Override
    public void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Parent)event.getSource()).getScene().getWindow();
        if(Main.app.getLoggedUser() != null){
            Main.app.logout();
        }
        stage.setScene(new Scene(
                FXMLLoader.load(getClass().getResource("./../layouts/login_layout.fxml"))
        ));
        stage.show();
    }

}
