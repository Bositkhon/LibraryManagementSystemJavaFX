package sample;

import helpers.API;
import helpers.Db;
import javafx.application.Application;
import javafx.stage.Stage;
import models.BookByISBN;
import models.User;
import org.apache.derby.optional.json.SimpleJsonTool;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.parser.Parser;
import java.awt.print.Book;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.UnknownServiceException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.PreparedStatement;
import java.util.ArrayList;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("./../layouts/admin.fxml"));
//        primaryStage.setTitle("Library Management System");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();

        /*primaryStage = FXMLLoader.load(getClass().getResource("./../layouts/create_user_layout.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.show();*/
//        User user = new User();
//        System.out.println(user.isNewRecord());
//        user.setUsername("Bositkhon");
//        user.setPassword("bosit4me");
//        user.setRoleID(1);
//        if(user.save()){
//            System.out.println("User has been added");
//        }else{
//            System.out.println("Something wrong");
//        }

        /*PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "INSERT INTO USERS (PASSWORD, USERNAME, ROLE_ID) VALUES (?, ?, ?)"
        );
        preparedStatement.setString(1, "123");
        preparedStatement.setString(2, "shukur");
        preparedStatement.setInt(3, 1);

        if(preparedStatement.executeUpdate() > 0){
            System.out.println("Success");
        }else{
            System.out.println("Failure");
        }*/

//        String url = "https://jsonplaceholder.typicode.com/posts/1";
//        String url = "https://isbnsearch.org/isbn/0684843285";
        String url = "https://isbnsearch.org/isbn/9781305272378";
//        InputStreamReader reader = new InputStreamReader(new URL(url).openStream());
//        int t;
//        String read_reslt="";
//
//        // Use of read() method
//        while((t = reader.read()) != -1)
//        {
//            read_reslt = read_reslt+(char)t;
//        }

        // print the result read from the file
//        System.out.println(read_reslt);

//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest req = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .header("Content-type","text/html")
//                .build();
//        client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
//        .thenApply(resp->{
//            System.out.println(resp.body());
//            return resp;
//        })
//        .thenApply(HttpResponse::body);
        System.out.println("+++++++=======================");
//        BookByISBN b = new BookByISBN();
//        b = API.getFromAPI(url);

        System.out.println(API.getFromAPI("https://isbnsearch.org/isbn/9781133710882"));
        System.out.println(API.getFromAPI("https://isbnsearch.org/isbn/9780840068071"));
        System.out.println(API.getFromAPI("https://isbnsearch.org/isbn/9781305272378"));
        System.out.println(API.getFromAPI(url));
//        Connection.Response response;
//        try {
//            response = Jsoup.connect("https://isbnsearch.org/isbn/9781133710882").followRedirects(false).execute();
//            System.out.println(response.statusCode());
//        }catch (HttpStatusException err){
//            System.out.println("404 Not Found");
//        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
