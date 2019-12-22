package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private Tab issueBooksTab;

    @FXML
    private Tab myIssuedBooksTab;

    @FXML
    private Tab myFinedBooksTab;

    @FXML
    private Tab networkTab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent parent1 = FXMLLoader.load(getClass().getResource("../layouts/student/student_issue_layout.fxml"));
            issueBooksTab.setContent(parent1);
            Parent parent2 = FXMLLoader.load(getClass().getResource("../layouts/student/student_issued_books_layout.fxml"));
            myIssuedBooksTab.setContent(parent2);
            Parent parent3 = FXMLLoader.load(getClass().getResource("../layouts/student/student_fined_books_layout.fxml"));
            myFinedBooksTab.setContent(parent3);
            Parent parent4 = FXMLLoader.load(getClass().getResource("../layouts/student/networking_layout.fxml"));
            networkTab.setContent(parent4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search(){

    }

}
