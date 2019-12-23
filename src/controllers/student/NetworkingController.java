package controllers.student;

import helpers.API;
import helpers.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.w3c.dom.Text;
import sample.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NetworkingController {

    @FXML
    private TextField txtISBN;

    @FXML
    private Label titleBook;

    @FXML
    private ImageView imgBook;

    @FXML
    private Label fieldsBook;

    public void sendRequest() {
        System.out.println(txtISBN.getText());
        String isbn = txtISBN.getText().trim();
        if (isbn.isEmpty()) {
            AlertBox.error("ISBN can not be empty");
        } else {
            ArrayList<String> data;
            data = API.getFromAPI(isbn);
            if (data != null) {
                if (data.get(0).toString().equals("vpn")){
                    AlertBox.error("Please use VPN");
                }else {
                    System.out.println(data.get(0));
                    titleBook.setText(data.get(1));

                    Image image = new Image(data.get(0));
                    imgBook.setImage(image);

                    StringBuilder fields = new StringBuilder();
                    for (int i = 2; i < data.size(); i++) {
                        fields.append(data.get(i));
                        fields.append("\n");
                    }
                    fieldsBook.setText(fields.toString());
                }
            } else {
                AlertBox.error("Book not found");
            }
        }
    }
}
