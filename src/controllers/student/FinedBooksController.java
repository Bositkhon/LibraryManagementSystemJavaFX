package controllers.student;

import entities.IssuePeriod;
import entities.IssuedBook;
import entities.IssuedBooksFine;
import helpers.AlertBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.IssuedBookModel;
import models.IssuedBooksFineModel;
import sample.Main;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FinedBooksController implements Initializable {

    @FXML
    public TableView<IssuedBooksFine> finesTableView;
    public TableColumn<IssuedBooksFine, Integer> serialTableColumn;
    public TableColumn<IssuedBooksFine, String> titleTableColumn;
    public TableColumn<IssuedBooksFine, String> subjectTableColumn;
    public TableColumn<IssuedBooksFine, IssuePeriod> periodTableColumn;
    public TableColumn<IssuedBooksFine, String> authorTableColumn;
    public TableColumn<IssuedBooksFine, String> isbnTableColumn;
    public TableColumn<IssuedBooksFine, Date> publishedDateTableColumn;
    public TableColumn<IssuedBooksFine, String> reasonTableColumn;
    public Button resetSearchButton;
    public TextField searchTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        serialTableColumn.setCellValueFactory(cell -> {
            return new ReadOnlyObjectWrapper<>(cell.getTableView().getItems().indexOf(cell.getValue()) + 1);
        });

        titleTableColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getIssuedBook().getBook().getTitle());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        subjectTableColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getIssuedBook().getBook().getSubject());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        periodTableColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getIssuedBook().getPeriod());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        authorTableColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getIssuedBook().getBook().getAuthor());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        isbnTableColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getIssuedBook().getBook().getIsbn());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        publishedDateTableColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getIssuedBook().getBook().getPublishedDate());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        reasonTableColumn.setCellValueFactory(cell -> {
            return new ReadOnlyObjectWrapper<>(cell.getValue().getReason());
        });

        resetSearchButton.setOnAction(e -> initialize(url, resourceBundle));

        try {
            IssuedBookModel issuedBookModel = new IssuedBookModel();
            ObservableList<IssuedBook> issuedBooks = FXCollections.observableArrayList(issuedBookModel.getAllByUserId(Main.app.getLoggedUser().getId()));
            ObservableList<IssuedBooksFine> fines = FXCollections.observableArrayList();
            for(IssuedBook issuedBook : issuedBooks){
                if(issuedBook.getFine() != null){
                    fines.add(issuedBook.getFine());
                }
            }
            finesTableView.setItems(fines);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void search() throws SQLException {
        if(!searchTextField.getText().isEmpty()){
            IssuedBooksFineModel issuedBooksFineModel = new IssuedBooksFineModel();
            ObservableList<IssuedBooksFine> fines = FXCollections.observableArrayList(issuedBooksFineModel.search(searchTextField.getText()));
            if(fines.isEmpty()){
                AlertBox.alert(Alert.AlertType.INFORMATION, "No such book");
            }else{
                this.finesTableView.setItems(fines);
            }
        }else{
            AlertBox.error("Field can not be empty!");
        }
    }
}
