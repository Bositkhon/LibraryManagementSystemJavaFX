package controllers.student;

import entities.IssuePeriod;
import entities.IssuedBook;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.BookModel;
import models.IssuedBookModel;
import sample.Main;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;

public class IssuedBooksController implements Initializable {

    @FXML
    private TableView<IssuedBook> issuedBooksTableView;

    @FXML
    private TableColumn<IssuedBook, Integer> serialTableColumn;

    @FXML
    private TableColumn<IssuedBook, IssuedBook.Status> statusTableColumn;

    @FXML
    private TableColumn<IssuedBook, String> titleTableColumn;

    @FXML
    private TableColumn<IssuedBook, String> subjectTableColumn;

    @FXML
    private TableColumn<IssuedBook, IssuePeriod> periodTableColumn;

    @FXML
    private TableColumn<IssuedBook, Date> dueDateTableColumn;

    @FXML
    private TableColumn<IssuedBook, String> authorTableColumn;

    @FXML
    private TableColumn<IssuedBook, String> ISBNTableColumn;

    @FXML
    private TableColumn<IssuedBook, Date> publishedDateTableColumn;

    @FXML
    private TableColumn<IssuedBook, Timestamp> createdAtTableColumn;

    public Button resetButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        serialTableColumn.setCellValueFactory(column -> {
            return new ReadOnlyObjectWrapper<Integer>(column.getTableView().getItems().indexOf(column.getValue()) + 1);
        });

        statusTableColumn.setCellValueFactory(column -> {
            System.out.println(column.getValue().getStatus());
            return new ReadOnlyObjectWrapper<>(column.getValue().getStatus());
        });

        titleTableColumn.setCellValueFactory(column -> {
            try {
                return new ReadOnlyObjectWrapper<String>(column.getValue().getBook().getTitle());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        subjectTableColumn.setCellValueFactory(column -> {
            try {
                return new ReadOnlyObjectWrapper<String>(column.getValue().getBook().getSubject());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        periodTableColumn.setCellValueFactory(column -> {
            try {
                return new ReadOnlyObjectWrapper<IssuePeriod>(column.getValue().getIssuePeriod());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        dueDateTableColumn.setCellValueFactory(column -> {
            return new ReadOnlyObjectWrapper<>(column.getValue().getDue());
        });

        authorTableColumn.setCellValueFactory(column -> {
            try {
                return new ReadOnlyObjectWrapper<>(column.getValue().getBook().getAuthor());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        ISBNTableColumn.setCellValueFactory(column -> {
            try {
                return new ReadOnlyObjectWrapper<>(column.getValue().getBook().getIsbn());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        publishedDateTableColumn.setCellValueFactory(column -> {
            try {
                return new ReadOnlyObjectWrapper<>(column.getValue().getBook().getPublishedDate());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        createdAtTableColumn.setCellValueFactory(cell -> {
            return new ReadOnlyObjectWrapper<>(cell.getValue().getIssuedAt());
        });

        resetButton.setOnAction(e -> initialize(url,resourceBundle));

        try{
            IssuedBookModel model = new IssuedBookModel();
            this.issuedBooksTableView.getItems().addAll(model.getAllByUserId(Main.app.getLoggedUser().getId()));
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void search(){

    }

}
