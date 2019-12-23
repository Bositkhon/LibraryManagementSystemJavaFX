package controllers.administrator;

import entities.Book;
import helpers.AlertBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.BookModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyBookFormController implements Initializable {

    private Book book;
    private TableView<Book> bookTableView;
    private Integer index;
    private boolean isInitialized = false;

    public TextField titleTextField;
    public TextField quantityTextField;
    public TextField subjectTextField;
    public TextField authorTextField;
    public TextField isbnTextField;
    public DatePicker publishedDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            if(!isInitialized){
                AlertBox.error("Book is not initialized");
            }else{
                titleTextField.setText(this.book.getTitle());
                quantityTextField.setText(String.valueOf(this.book.getQuantity()));
                subjectTextField.setText(this.book.getSubject());
                authorTextField.setText(this.book.getAuthor());
                isbnTextField.setText(this.book.getIsbn());
                publishedDatePicker.setValue(this.book.getPublishedDate().toLocalDate());
            }
        });
    }

    public void modify(ActionEvent event) {
        this.book.setTitle(titleTextField.getText());
        this.book.setSubject(subjectTextField.getText());
        this.book.setAuthor(authorTextField.getText());
        this.book.setIsbn(isbnTextField.getText());
        this.book.setQuantity(Integer.parseInt(quantityTextField.getText()));
        this.book.setPublishedDate(Date.valueOf(publishedDatePicker.getValue()));
        BookModel bookModel = new BookModel();
        try {
            if(bookModel.update(this.book)){
                this.bookTableView.getItems().set(this.index, this.book);
                AlertBox.success("Book has successfully been updated");
                ((Stage)((Parent)event.getSource()).getScene().getWindow()).close();
            }else{
                AlertBox.error("Book can not be updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void set(Book book, TableView<Book> bookTableView, Integer index){
        this.book = book;
        this.bookTableView = bookTableView;
        this.index = index;
        this.isInitialized = true;
    }

}
