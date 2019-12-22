package controllers.librarian;

import entities.IssuedBook;
import entities.IssuedBooksFine;
import helpers.AlertBox;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.IssuedBooksFineModel;

import java.sql.SQLException;

public class FineController {

    private IssuedBook issuedBook;
    private boolean isInitialized;

    public TextField reasonTextField;

    public void fine(ActionEvent event) {
        if(isInitialized){
            if(reasonTextField.getText().isEmpty()){
                AlertBox.error("Reason can not be empty");
            }else{
                IssuedBooksFineModel issuedBooksFineModel = new IssuedBooksFineModel();
                IssuedBooksFine fine = new IssuedBooksFine();
                fine.setIssuedBookId(issuedBook.getId());
                fine.setReason(reasonTextField.getText());
                try {
                    if(issuedBooksFineModel.insert(fine)){
                        AlertBox.success("Issue has successfully been fined");
                        ((Stage)((Parent)event.getSource()).getScene().getWindow()).close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setIssuedBook(IssuedBook issuedBook) {
        this.issuedBook = issuedBook;
        this.isInitialized = true;
    }

}
