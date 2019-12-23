package controllers.student;

import entities.Book;
import entities.Reservation;
import helpers.AlertBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import models.ReservationModel;
import sample.Main;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReservationFormController implements Initializable {

    private boolean isInitialized;
    private Book book;

    @FXML
    private DatePicker datePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            if(!isInitialized){
                AlertBox.error("Book has not been passed");
            }
        });
    }

    public void reserve(ActionEvent event){
        if(isInitialized){
            ReservationModel reservationModel = new ReservationModel();
            Reservation reservation = new Reservation();
            reservation.setUserId(Main.app.getLoggedUser().getId());
            reservation.setBookId(book.getId());
            reservation.setReserveDate(Date.valueOf(datePicker.getValue()));
            try{
                if(reservationModel.insert(reservation)){
                    AlertBox.success("Book has been reserved successfully");
                    ((Stage)((Parent)event.getSource()).getScene().getWindow()).close();
                }else{
                    AlertBox.error("Book can not be reserved");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void setBook(Book book) {
        this.book = book;
        this.isInitialized = true;
    }
}
