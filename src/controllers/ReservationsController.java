package controllers;

import entities.IssuedBook;
import entities.Reservation;
import helpers.AlertBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.ReservationModel;
import sample.Main;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ReservationsController implements Initializable {

    @FXML
    public TableView<Reservation> reservationsTableView;

    @FXML
    public TableColumn<Reservation, Integer> serialTableColumn;

    @FXML
    public TableColumn<Reservation, String> titleTableColumn;

    @FXML
    public TableColumn<Reservation, String> subjectTableColumn;

    @FXML
    public TableColumn<Reservation, String> authorTableColumn;

    @FXML
    public TableColumn<Reservation, String> isbnTableColumn;

    @FXML
    public TableColumn<Reservation, Date> publishedDateTableColumn;

    @FXML
    public TableColumn<Reservation, Timestamp> createdAtTableColumn;

    @FXML
    public TableColumn<Reservation, Void> deleteTableColumn;

    @FXML
    public TextField searchTextField;

    @FXML
    public Button resetSearchButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReservationModel reservationModel = new ReservationModel();

        serialTableColumn.setCellValueFactory(cell -> {
            return new ReadOnlyObjectWrapper<>(cell.getTableView().getItems().indexOf(cell.getValue()) + 1);
        });

        titleTableColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getBook().getTitle());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        subjectTableColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getBook().getSubject());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        authorTableColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getBook().getAuthor());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        isbnTableColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getBook().getIsbn());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        publishedDateTableColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getBook().getPublishedDate());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        createdAtTableColumn.setCellValueFactory(cell -> {
            try {
                return new ReadOnlyObjectWrapper<>(cell.getValue().getBook().getCreatedAt());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        deleteTableColumn.setCellFactory(cell -> {
            return new TableCell<>(){
                private final Button button = new Button("Delete");
                {
                    button.setOnAction(event -> {
                        Reservation reservation = getTableView().getItems().get(getIndex());
                        ReservationModel model = new ReservationModel();
                        try {
                            if(reservationModel.delete(reservation.getId())){
                                getTableView().getItems().remove(getIndex());
                                AlertBox.success("Reservation has successfully been deleted");
                            }else{
                                AlertBox.error("Reservation can not be deleted");
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

        try {
            ObservableList<Reservation> reservations = FXCollections.observableArrayList(reservationModel.getAllByUserId(Main.app.getLoggedUser().getId()));
            this.reservationsTableView.setItems(reservations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void search(ActionEvent event){

    }

}
