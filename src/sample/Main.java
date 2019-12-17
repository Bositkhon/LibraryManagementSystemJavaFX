package sample;

import entities.Role;
import entities.User;
import javafx.application.Application;
import javafx.stage.Stage;
import models.UserModel;


public class Main extends Application {

    public static App app = new App();

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

        UserModel userModel = new UserModel();
        User user = new User();
        user.setUsername("Bositkhon");
        user.setPassword("bosit4me");
        user.setRoleId(1);
        if(userModel.insert(user)){
            System.out.println("Success");
        }else{
            for(String error : user.getErrors()){
                System.out.println(error);
            }
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
