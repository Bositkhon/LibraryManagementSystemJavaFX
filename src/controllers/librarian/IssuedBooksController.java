package controllers.librarian;

import entities.IssuedBook;
import entities.IssuedBooksFine;
import helpers.AlertBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
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
import models.IssuedBookModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

public class IssuedBooksController implements Initializable {

//    public TableColumn statusTableColumn;

    @FXML
    private TableColumn<IssuedBook, String> statusTableColumn;

    @FXML
    private TableView<IssuedBook> issuedBooksTable;

    @FXML
    private TableColumn<IssuedBook, Integer> serialTableColumn;

    @FXML
    private TableColumn<IssuedBook, String> usernameTableColumn;

    @FXML
    private TableColumn<IssuedBook, String> bookTitleTableColumn;

    @FXML
    private TableColumn<IssuedBook, String> periodTableColumn;

    @FXML
    private TableColumn<IssuedBook, Date> dueDateTableColumn;

    @FXML
    private TableColumn<IssuedBook, String> issuedDateTableColumn;

    @FXML
    private TableColumn<IssuedBook, Void> returnTableColumn;

    @FXML
    private TableColumn<IssuedBook, Void> fineTableColumn;

    public TextField searchTextField;

    public Button resetButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        IssuedBookModel issuedBookModel = new IssuedBookModel();

        serialTableColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getTableView().getItems().indexOf(cell.getValue()) + 1));

        statusTableColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getStatus().getTitle()));

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
            return new TableCell<>() {
                private final Button button = new Button("Return");

                {
                    button.setOnAction(event -> {
                        IssuedBook issuedBook = getTableView().getItems().get(getIndex());
                        IssuedBookModel issuedBookModel1 = new IssuedBookModel();
                        try {
                            System.out.println(issuedBook.getId());
                            if (issuedBookModel.delete(issuedBook.getId())) {
                                getTableView().getItems().remove(issuedBook);
                                AlertBox.success("Issue has successfully been deleted");
                            } else {
                                AlertBox.error("Issue can not be deleted");
                            }
                        } catch (SQLException e) {
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

        fineTableColumn.setCellFactory(cell -> {
            return new TableCell<>() {
                private final Button button = new Button("Fine");
                private IssuedBooksFine fine;
                {
                    button.setOnAction(event -> {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("./../../layouts/librarian/fine_form_layout.fxml"));
                        try {
                            Parent parent = loader.load();
                            FineFormController controller = loader.getController();
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
                    if (b) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            };
        });

        try {
            ObservableList<IssuedBook> books = FXCollections.observableArrayList(issuedBookModel.getAll());
            issuedBooksTable.setItems(books);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resetButton.setOnAction(e -> initialize(url, resourceBundle));

    }

    public void search() {
        if(searchTextField.getText().isEmpty()){
            AlertBox.error("Field can not be empty");
        }else{
            try {
                IssuedBookModel issuedBookModel = new IssuedBookModel();
                ObservableList<IssuedBook> list = FXCollections.observableArrayList(issuedBookModel.search(searchTextField.getText()));
                if(list.isEmpty()){
                    AlertBox.alert(Alert.AlertType.INFORMATION, "No such book");
                }else{
                    issuedBooksTable.setItems(list);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

