package controllers.student;

import helpers.API;
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

public class Networking implements Initializable {

    @FXML
    private TextField txtISBN;

    @FXML
    private Label titleBook;

    @FXML
    private ImageView imgBook;

    @FXML
    private Label fieldsBook;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void sendRequest(ActionEvent event) throws IOException {
        System.out.println(txtISBN.getText());
        String isbn = txtISBN.getText().trim();
        ArrayList<String> data = new ArrayList<String>();
        data = API.getFromAPI(isbn);
        System.out.println(data.get(0));
        titleBook.setText(data.get(1));

        Image image = new Image(data.get(0).toString());
        imgBook.setImage(image);

        String fields="";
        for (int i = 2; i<data.size();i++){
            fields+=data.get(i);
            fields+="\n";
        }
        fieldsBook.setText(fields);
    }
}
