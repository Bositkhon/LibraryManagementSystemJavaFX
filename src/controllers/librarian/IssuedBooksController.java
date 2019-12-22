package controllers.librarian;

import entities.IssuePeriod;
import entities.IssuedBook;
import entities.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.IssuedBookModel;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class IssuedBooksController implements Initializable {

    public TableColumn statusTableColumn    ;
    @FXML private TableView<IssuedBook> issuedBooksTable;

    @FXML private TableColumn<IssuedBook, Integer> serialTableColumn;

    @FXML private TableColumn<IssuedBook, String>  usernameTableColumn;

    @FXML private TableColumn<IssuedBook, String> bookTitleTableColumn;

    @FXML private TableColumn<IssuedBook, String> periodTableColumn;

    @FXML private TableColumn<IssuedBook, Date> dueDateTableColumn;

    @FXML private TableColumn<IssuedBook, String> issuedDateTableColumn;

    @FXML private TableColumn<IssuedBook, Void> returnTableColumn;

    @FXML private TableColumn<IssuedBook, Void> fineTableColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        IssuedBookModel issuedBookModel = new IssuedBookModel();

        serialTableColumn.setCellValueFactory(
                new PropertyValueFactory<IssuedBook, Integer>("id")
        );

        usernameTableColumn.setCellValueFactory(column -> {
            try {
                return new SimpleStringProperty(column.getValue().getUser().getUsername());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        bookTitleTableColumn.setCellValueFactory(column -> {
            try {
                return new SimpleStringProperty(column.getValue().getBook().getTitle());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        periodTableColumn.setCellValueFactory(column -> {
            try {
                return new SimpleStringProperty(column.getValue().getPeriod().getTitle());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        dueDateTableColumn.setCellValueFactory(column -> {
            return new SimpleObjectProperty<>(column.getValue().getDue());
        });

        issuedDateTableColumn.setCellValueFactory(column -> {
            return new SimpleStringProperty(column.getValue().getIssuedAt().toString());
        });

        returnTableColumn.setCellFactory(issuedBookVoidTableColumn -> {
            return new TableCell<>(){
                private final Button button = new Button("Return");

                @Override
                protected void updateItem(Void aVoid, boolean b) {
                    super.updateItem(aVoid, b);
                    if(b){
                        setGraphic(null);
                    }else{
                        setGraphic(button);
                    }
                }
            };
        });

        fineTableColumn.setCellFactory(issuedBookVoidTableColumn -> {
            return new TableCell<>(){
                private final Button button = new Button("Fine");
                {
                    button.setOnAction(event -> {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("./../../layouts/librarian/fine_form_layout.fxml"));
                        try {
                            Parent parent = loader.load();
                            FineController controller = loader.getController();
                            controller.setIssuedBook(getTableView().getItems().get(getIndex()));
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                @Override
                protected void updateItem(Void aVoid, boolean b) {
                    super.updateItem(aVoid, b);
                    if(b){
                        setGraphic(null);
                    }else{
                        setGraphic(button);
                    }
                }
            };
        });


        try {
            ObservableList<IssuedBook> books = FXCollections.observableArrayList(issuedBookModel.getAll());
            issuedBooksTable.getItems().addAll(books);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addBook(){

    }

    public void searchIssuedBook(){

    }

    public void clear(){

    }

}

