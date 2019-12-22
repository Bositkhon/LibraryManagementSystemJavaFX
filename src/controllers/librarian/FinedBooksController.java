package controllers.librarian;

import entities.IssuedBooksFine;
import helpers.AlertBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.IssuedBooksFineModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FinedBooksController implements Initializable {

    public TableView<IssuedBooksFine> issuedBooksFineTableView;

    public TableColumn<IssuedBooksFine, Integer> serialTableColumn;
    public TableColumn<IssuedBooksFine, String> usernameTableColumn;
    public TableColumn<IssuedBooksFine, String> titleTableColumn;
    public TableColumn<IssuedBooksFine, String> subjectTableColumn;
    public TableColumn<IssuedBooksFine, String> authorTableColumn;
    public TableColumn<IssuedBooksFine, String> isbnTableColumn;
    public TableColumn<IssuedBooksFine, String> reasonTableColumn;
    public TableColumn<IssuedBooksFine, Void> unfineTableColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IssuedBooksFineModel issuedBooksFineModel = new IssuedBooksFineModel();

        serialTableColumn.setCellValueFactory(cell -> {
            return new ReadOnlyObjectWrapper<>(cell.getTableView().getItems().indexOf(cell.getValue()) + 1);
        });

        usernameTableColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getIssuedBook().getUser().getUsername());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
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

        reasonTableColumn.setCellValueFactory(cell -> {
            return new ReadOnlyObjectWrapper<>(cell.getValue().getReason());
        });

        unfineTableColumn.setCellFactory(cell -> {
            return new TableCell<>() {
                private final Button button = new Button("Delete fine");

                {
                    button.setOnAction(event -> {
                        IssuedBooksFine issuedBooksFine = getTableView().getItems().get(getIndex());
                        IssuedBooksFineModel issuedBooksFineModel = new IssuedBooksFineModel();
                        try {
                            if (issuedBooksFineModel.delete(issuedBooksFine.getId())) {
                                getTableView().getItems().remove(issuedBooksFine);
                                AlertBox.success("Fine has successfully been deleted");
                            } else {
                                AlertBox.error("Fine can not be deleted");
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

        try {
            ObservableList<IssuedBooksFine> fines = FXCollections.observableArrayList(issuedBooksFineModel.getAll());
            System.out.println(fines);
            issuedBooksFineTableView.setItems(fines);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
