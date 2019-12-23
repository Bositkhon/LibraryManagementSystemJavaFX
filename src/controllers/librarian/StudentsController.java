package controllers.librarian;

import entities.Role;
import entities.User;
import helpers.AlertBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import models.RoleModel;
import models.UserModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, Integer> serialTableColumn;

    @FXML
    private TableColumn<User, String> usernameTableColumn;

    @FXML
    private TableColumn<User, Role> roleTableColumn;

    @FXML
    private TableColumn<User, String> createdAtTableColumn;

    @FXML
    private TableColumn<User, Void> deleteTableColumn;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TableColumn<User, Void> changePasswordTableColumn;

    @FXML
    private Button addUserButton;

    @FXML
    private Button resetSearchButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userTableView.setEditable(true);
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            RoleModel roleModel = new RoleModel();
            Role role = roleModel.getByTitle("Student");
            users = FXCollections.observableArrayList(
                    (new UserModel()).getByRole(role)
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        serialTableColumn.setCellValueFactory(cell -> {
            return new ReadOnlyObjectWrapper<>(cell.getTableView().getItems().indexOf(cell.getValue()) + 1);
        });
        usernameTableColumn.setCellValueFactory(
                new PropertyValueFactory<>("username")
        );

        usernameTableColumn.setCellFactory(TextFieldTableCell.<User>forTableColumn());

        roleTableColumn.setCellValueFactory(role -> {
            User user = role.getValue();
            try {
                return new SimpleObjectProperty<Role>(user.getRole());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        try {
            ObservableList<Role> roles = FXCollections.observableArrayList((new RoleModel()).getAll());
            roleTableColumn.setCellFactory(ChoiceBoxTableCell.<User, Role>forTableColumn(roles));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        roleTableColumn.setOnEditCommit( (TableColumn.CellEditEvent<User, Role> event)->{
            TablePosition<User, Role> pos = event.getTablePosition();
            Role new_role = event.getNewValue();
            int row = pos.getRow();
            User user = event.getTableView().getItems().get(row);
            user.setRoleId(new_role.getId());
        });

        createdAtTableColumn.setCellValueFactory(timestamp -> new SimpleStringProperty(timestamp.getValue().getCreatedAt().toString()));

        userTableView.setItems(users);


        usernameTableColumn.setOnEditCommit( (TableColumn.CellEditEvent<User, String> event) -> {
            UserModel userModel = new UserModel();
            TablePosition<User, String> pos = event.getTablePosition();
            String new_value = event.getNewValue();
            int row = pos.getRow();
            User user = event.getTableView().getItems().get(row);
            user.setUsername(new_value);
            try {
                if(userModel.update(user)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("User has successfully been updated");
                    alert.setContentText(null);
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("The user could not be updated");
                    alert.setContentText(user.getErrors().toString());
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } );

        deleteTableColumn.setCellFactory(userButtonTableColumn -> {
            return new TableCell<User, Void>(){
                private Button button = new Button("Delete");
                {
                    button.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        getTableView().getItems().remove(user);
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

        resetSearchButton.setOnAction(event -> initialize(url, resourceBundle));

    }

    public void addUser(ActionEvent event){
        UserModel userModel = new UserModel();
        try {
            User user = userModel.getByUsername(usernameTextField.getText());
            if(user != null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("User with such username already exists");
                alert.setContentText(null);
                alert.showAndWait();
            }else{
                User new_user = new User();
                new_user.setUsername(usernameTextField.getText());
                new_user.setPassword(passwordField.getText());
                RoleModel roleModel = new RoleModel();
                Role studentRole = roleModel.getByTitle("Student");
                new_user.setRoleId(studentRole.getId());
                if(userModel.insert(new_user)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("User has successfully been added");
                    alert.showAndWait();
                    userTableView.getItems().add(userModel.getByUsername(new_user.getUsername()));
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Something went wrong");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private TextField searchTextField;

    public void search(){
        UserModel userModel = new UserModel();
        if(!searchTextField.getText().isEmpty()){
            try{
                User user = userModel.search(searchTextField.getText());
                if(user != null){
                    this.userTableView.getItems().clear();
                    this.userTableView.getItems().add(user);
                }else{
                    AlertBox.alert(Alert.AlertType.WARNING, "No such user");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else {
            AlertBox.alert(Alert.AlertType.WARNING, "Search field should not be empty");
        }
    }

}
