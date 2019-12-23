package controllers.student;

import entities.Book;
import helpers.AlertBox;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.BookModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class BooksController implements Initializable {

    @FXML
    private TableView<Book> booksTableView;

    @FXML
    private TableColumn<Book, Integer> serialTableColumn;

    @FXML
    private TableColumn<Book, String> titleTableColumn;

    @FXML
    private TableColumn<Book, Integer> quantityTableColumn;

    @FXML
    private TableColumn<Book, String> subjectTableColumn;

    @FXML
    private TableColumn<Book, String> authorTableColumn;

    @FXML
    private TableColumn<Book, String> isbnTableColumn;

    @FXML
    private TableColumn<Book, String> publishedDateTableColumn;

    @FXML
    private TableColumn<Book, String> createdAtTableColumn;

    @FXML
    private TableColumn<Book, Void> actionsTableColumn;

    @FXML
    private TableColumn<Book, Void> reserveTableColumn;

    @FXML
    private TableColumn<Book, Void> issueTableColumn;

    @FXML
    private Button refreshButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            try {
                BookModel bookModel = new BookModel();

                serialTableColumn.setCellValueFactory(column -> {
                    return new ReadOnlyObjectWrapper<Integer>(column.getTableView().getItems().indexOf(column.getValue()) + 1);
                });

                titleTableColumn.setCellValueFactory(
                        new PropertyValueFactory<Book, String>("title")
                );

                quantityTableColumn.setCellValueFactory(
                        new PropertyValueFactory<Book, Integer>("quantity")
                );

                subjectTableColumn.setCellValueFactory(
                        new PropertyValueFactory<Book, String>("subject")
                );

                authorTableColumn.setCellValueFactory(
                        new PropertyValueFactory<Book, String>("author")
                );

                isbnTableColumn.setCellValueFactory(
                        new PropertyValueFactory<Book, String>("isbn")
                );

                publishedDateTableColumn.setCellValueFactory(column -> {
                    return new SimpleStringProperty(new SimpleDateFormat("yyyy-MM-dd").format(column.getValue().getPublishedDate()));
                });

                createdAtTableColumn.setCellValueFactory(column -> {
                    return new SimpleStringProperty(new SimpleDateFormat("yyyy-MM-dd").format(column.getValue().getCreatedAt()));
                });

                issueTableColumn.setCellFactory(bookVoidTableColumn -> {
                    return new TableCell<Book, Void>() {
                        private final Button issueButton = new Button("Issue");

                        {
                            issueButton.setOnAction(event -> {
                                Book book = getTableView().getItems().get(getIndex());
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layouts/student/issue_book_form_layout.fxml"));
                                    Parent parent = loader.load();
                                    IssueBookFormController controller = loader.<IssueBookFormController>getController();
                                    controller.setBook(book);
                                    Stage stage = new Stage();
                                    stage.setTitle("Issue a book for a period");
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
                            if (b) {
                                setGraphic(null);
                            } else {
                                setGraphic(issueButton);
                            }
                        }
                    };
                });

                reserveTableColumn.setCellFactory(bookVoidTableColumn -> {
                    return new TableCell<>() {
                        private final Button button = new Button("Reserve");
                        {
                            button.setOnAction(event -> {
                                Book book = getTableView().getItems().get(getIndex());
                                Stage stage = new Stage();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("./../../layouts/student/reservation_form_layout.fxml"));
                                try {
                                    Parent parent = loader.load();
                                    ReservationFormController controller = loader.getController();
                                    controller.setBook(book);
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
                            if (b) {
                                setGraphic(null);
                            } else {
                                setGraphic(button);
                            }
                        }
                    };
                });

                refreshButton.setOnAction(event -> {
                    initialize(url, resourceBundle);
                });

                ObservableList<Book> books = null;

                books = FXCollections.observableArrayList((new BookModel()).getAll());
                booksTableView.setItems(books);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private TextField searchTextField;

    public void search() {
        BookModel bookModel = new BookModel();
        if(!searchTextField.getText().isEmpty()){
            try {
                ObservableList<Book> books = FXCollections.observableArrayList(bookModel.search(searchTextField.getText()));
                if(!books.isEmpty()){
                    this.booksTableView.getItems().clear();
                    this.booksTableView.getItems().addAll(books);
                }else{
                    AlertBox.alert(Alert.AlertType.WARNING, "No such book");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            AlertBox.alert(Alert.AlertType.WARNING, "Search field should not be empty");
        }
    }
}
