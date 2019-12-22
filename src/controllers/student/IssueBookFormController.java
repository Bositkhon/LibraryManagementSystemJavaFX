package controllers.student;

import entities.Book;
import entities.IssuePeriod;
import entities.IssuedBook;
import helpers.AlertBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import models.IssuePeriodModel;
import models.IssuedBookModel;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ResourceBundle;

public class IssueBookFormController implements Initializable {

    @FXML
    private ChoiceBox<IssuePeriod> periodChoiceBox;

    private boolean isInitialized = false;
    private Book book;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            if(!isInitialized){
                AlertBox.error("Book has not been initialized");
            }
            IssuePeriodModel issuePeriodModel = new IssuePeriodModel();
            try {
                periodChoiceBox.getItems().addAll(issuePeriodModel.getAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
            this.book = book;
            this.isInitialized = true;
    }

    public void issueBook(ActionEvent event){
        IssuePeriod period = periodChoiceBox.getSelectionModel().getSelectedItem();
        IssuedBookModel issuedBookModel = new IssuedBookModel();
        IssuedBook issuedBook = new IssuedBook();
        issuedBook.setBookId(book.getId());
        issuedBook.setPeriodId(period.getId());
        java.sql.Date date = new Date(System.currentTimeMillis());
        long time = date.getTime() + period.getDays() * 60 * 60 * 24;
        date.setTime(time);
        issuedBook.setDue(date);
        issuedBook.setUserId(Main.app.getLoggedUser().getId());
        try {
            if(issuedBookModel.insert(issuedBook)){
                AlertBox.success("Book has been issued");
                Stage stage = (Stage)((Parent)event.getSource()).getScene().getWindow();
                stage.close();
            }else{
                AlertBox.error("Something went wrong");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
