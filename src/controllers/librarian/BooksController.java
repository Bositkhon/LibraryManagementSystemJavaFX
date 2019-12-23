package controllers.librarian;

import controllers.administrator.ModifyUserFormController;
import entities.Book;
import helpers.AlertBox;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import models.BookModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
    private TableColumn<Book, Date> publishedDateTableColumn;

    @FXML
    private TableColumn<Book, String> createdAtTableColumn;

    @FXML
    private TableColumn<Book, Void> actionsTableColumn;

    @FXML
    private TableColumn<Book, Void> deleteTableColumn;

    public TableColumn<Book, Void> modifyTableColumn;

    @FXML
    private TableColumn<Book, Integer> statusTableColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SpinnerValueFactory<Integer> quantitySpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
        quantitySpinner.setValueFactory(quantitySpinnerFactory);

        BookModel bookModel = new BookModel();
        booksTableView.setEditable(true);
        try {

            serialTableColumn.setCellValueFactory(
                    new PropertyValueFactory<Book, Integer>("id")
            );

            titleTableColumn.setCellValueFactory(
                    new PropertyValueFactory<Book, String >("title")
            );

            titleTableColumn.setCellFactory(TextFieldTableCell.<Book>forTableColumn());

            titleTableColumn.setOnEditCommit(bookStringCellEditEvent -> {
                TablePosition<Book, String> position = bookStringCellEditEvent.getTablePosition();
                String new_value = bookStringCellEditEvent.getNewValue();
                int row = position.getRow();
                Book book = bookStringCellEditEvent.getTableView().getItems().get(row);
                book.setTitle(new_value);
                try {
                    if(bookModel.update(book)){
                        AlertBox.success("Book has successfully been updated");
                    }else{
                        AlertBox.success("Book could not be updated", book.getErrors().toString());
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            });

            quantityTableColumn.setCellValueFactory(
                    new PropertyValueFactory<Book, Integer>("quantity")
            );

            subjectTableColumn.setCellValueFactory(
                    new PropertyValueFactory<Book, String >("subject")
            );

            subjectTableColumn.setCellFactory(TextFieldTableCell.<Book>forTableColumn());

            subjectTableColumn.setOnEditCommit(bookStringCellEditEvent -> {
                TablePosition<Book, String> position = bookStringCellEditEvent.getTablePosition();
                int row = position.getRow();
                String new_value = bookStringCellEditEvent.getNewValue();
                Book book = bookStringCellEditEvent.getTableView().getItems().get(row);
                book.setSubject(new_value);
                try {
                    if(bookModel.update(book)){
                        AlertBox.alert(
                                Alert.AlertType.INFORMATION,
                                "Book has successfully been updated"
                        );
                    }else{
                        AlertBox.alert(
                                Alert.AlertType.ERROR,
                                "Book could not be updated"
                        );
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            authorTableColumn.setCellValueFactory(
                    new PropertyValueFactory<Book, String >("author")
            );

            authorTableColumn.setCellFactory(TextFieldTableCell.<Book>forTableColumn());

            authorTableColumn.setOnEditCommit(event -> {
                TablePosition<Book, String> position = event.getTablePosition();
                int row = position.getRow();
                String new_value = event.getNewValue();
                Book book = event.getTableView().getItems().get(row);
                book.setAuthor(new_value);
                try{
                    if(bookModel.update(book)){
                        AlertBox.success("Book has already been updated");
                    }else{
                        AlertBox.error("Book could not be updated", book.getErrors().toString());
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            });

            isbnTableColumn.setCellValueFactory(
                    new PropertyValueFactory<Book, String>("isbn")
            );

            isbnTableColumn.setCellFactory(TextFieldTableCell.<Book>forTableColumn());

            isbnTableColumn.setOnEditCommit(event -> {
                TablePosition<Book, String> position = event.getTablePosition();
                int row = position.getRow();
                String new_value = event.getNewValue();
                Book book = event.getTableView().getItems().get(row);
                try{
                    if(bookModel.update(book)){
                        AlertBox.success("Book has already been updated");
                    }else{
                        AlertBox.error("Book could not be updated", book.getErrors().toString());
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            });

            publishedDateTableColumn.setCellValueFactory(column -> {
                return new SimpleObjectProperty<>(column.getValue().getPublishedDate());
            });

            deleteTableColumn.setCellFactory(column -> {
                return new TableCell<Book, Void>(){
                    private Button button = new Button("Delete");
                    {
                        button.setOnAction(event -> {
                            Book book = getTableView().getItems().get(getIndex());
                            getTableView().getItems().remove(book);
                            try {
                                if(bookModel.delete(book.getId())){
                                    AlertBox.success("Book has successfully been deleted");
                                }else{
                                    AlertBox.error("Book could not be deleted");
                                }
                            } catch (SQLException e) {
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

            modifyTableColumn.setCellFactory(cell -> {
                return new TableCell<>(){
                    private Button button = new Button("Modify");
                    {
                        button.setOnAction(event -> {
                            try {
                                Book book = getTableView().getItems().get(getIndex());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("./../../layouts/librarian/modify_book_form_layout.fxml"));
                                Parent parent = loader.load();
                                ModifyBookFormController controller = loader.getController();
                                controller.set(book, getTableView(), getIndex());
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

            createdAtTableColumn.setCellValueFactory(property -> new SimpleStringProperty(property.getValue().getCreatedAt().toString()));

            ObservableList<Book> books = FXCollections.observableArrayList((new BookModel()).getAll());
            booksTableView.setItems(books);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField titleTextField, subjectTextField, authorTextField, isbnTextField;

    @FXML
    private DatePicker publishedDatePicker;

    @FXML
    private Spinner<Integer> quantitySpinner;

    public void addBook(ActionEvent event){
        BookModel bookModel = new BookModel();
        Book book = new Book();
        book.setTitle(titleTextField.getText());
        book.setSubject(subjectTextField.getText());
        book.setAuthor(authorTextField.getText());
        book.setIsbn(isbnTextField.getText());
        book.setQuantity(quantitySpinner.getValue());
        if(publishedDatePicker.getValue() != null){
            book.setPublishedDate(Date.valueOf(publishedDatePicker.getValue()));
        }
        try {
            if(bookModel.insert(book)){
                Book new_book = bookModel.getByIsbn(book.getIsbn());
                this.booksTableView.getItems().add(new_book);
                AlertBox.success("Book has successfully been added");
                clear();
            }else{
                AlertBox.error("Book could not be added", book.getErrors().toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear(){
        titleTextField.clear();
        authorTextField.clear();
        subjectTextField.clear();
        isbnTextField.clear();
        publishedDatePicker.getEditor().clear();
        quantitySpinner.getEditor().clear();
    }

    @FXML
    private TextField searchTextField;

    public void searchForBook(){
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
