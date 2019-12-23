package controllers.librarian;

import entities.Reservation;
import helpers.AlertBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.ReservationModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReservationsController implements Initializable {

    public TextField searchTextField;
    public Button resetButton;

    public TableView<Reservation> tableView;
    public TableColumn<Reservation, Integer> serialColumn;
    public TableColumn<Reservation, String> usernameColumn;
    public TableColumn<Reservation, String> titleColumn;
    public TableColumn<Reservation, String> subjectColumn;
    public TableColumn<Reservation, String> authorColumn;
    public TableColumn<Reservation, String> isbnColumn;
    public TableColumn<Reservation, Date> publishedDateColumn;
    public TableColumn<Reservation, Void> deleteColumn;
    public TableColumn<Reservation, Void> modifyColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReservationModel reservationModel = new ReservationModel();

        serialColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getTableView().getItems().indexOf(cell.getValue()) + 1));

        usernameColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getUser().getUsername());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        titleColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getBook().getTitle());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        subjectColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getBook().getSubject());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        authorColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getBook().getAuthor());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        isbnColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getBook().getIsbn());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        publishedDateColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getBook().getPublishedDate());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        deleteColumn.setCellFactory(cell -> {
            return new TableCell<>(){
                private final Button button = new Button("Delete");
                {
                    button.setOnAction(event -> {
                        Reservation reservation =  getTableView().getItems().get(getIndex());
                        ReservationModel reservationModel1 = new ReservationModel();
                        try{
                            if(reservationModel.delete(reservation.getId())){
                                getTableView().getItems().remove(reservation);
                                AlertBox.success("Reservation has successfully been deleted");
                            }else{
                                AlertBox.error("Reservation can not be deleted");
                            }
                        }catch (SQLException e){
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

        resetButton.setOnAction(event -> {
            initialize(url, resourceBundle);
        });

        try {
            ObservableList<Reservation> reservations = FXCollections.observableArrayList(reservationModel.getAll());
            tableView.setItems(reservations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void search(ActionEvent event) {
        if(searchTextField.getText().isEmpty()){
            AlertBox.error("Field can not be empty");
        }else{
            ReservationModel reservationModel = new ReservationModel();
            ObservableList<Reservation> reservations = null;
            try {
                reservations = FXCollections.observableArrayList(reservationModel.search(searchTextField.getText()));
                if(reservations.isEmpty()){
                    AlertBox.alert(Alert.AlertType.INFORMATION, "No such book");
                }else{
                    tableView.setItems(reservations);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
