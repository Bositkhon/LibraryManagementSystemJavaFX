package controllers.librarian;

import entities.Book;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyBookFormController implements Initializable {

    private Book book;
    private boolean isInitialized = false;

    public TextField titleTextField;
    public TextField quantityTextField;
    public TextField subjectTextField;
    public TextField authorTextField;
    public TextField isbnTextField;
    public DatePicker publishedDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void modify(ActionEvent event) {
    }

    public void setBook(Book book) {
        this.book = book;
        this.isInitialized = true;
    }
}
