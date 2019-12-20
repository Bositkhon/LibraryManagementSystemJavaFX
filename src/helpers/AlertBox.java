package helpers;

import javafx.scene.control.Alert;

public final class AlertBox {
    public static void alert(Alert.AlertType type, String header, String content){
        Alert alert = new Alert(type);
        switch (type){
            case ERROR:{
                alert.setTitle("Error");
                break;
            }
            case INFORMATION:{
                alert.setTitle("Success");
                break;
            }
            case NONE:{
                alert.setTitle("Alert");
                break;
            }
            case CONFIRMATION:{
                alert.setTitle("Confirm");
                break;
            }
            case WARNING:{
                alert.setTitle("Warning");
                break;
            }
        }
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void alert(Alert.AlertType type, String header){
        Alert alert = new Alert(type);
        switch (type){
            case ERROR:{
                alert.setTitle("Error");
                break;
            }
            case INFORMATION:{
                alert.setTitle("Success");
                break;
            }
            case NONE:{
                alert.setTitle("Alert");
                break;
            }
            case CONFIRMATION:{
                alert.setTitle("Confirm");
                break;
            }
            case WARNING:{
                alert.setTitle("Warning");
                break;
            }
        }
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    public static void success(String header, String content){
        alert(Alert.AlertType.INFORMATION, header, content);
    }

    public static void success(String header){
        alert(Alert.AlertType.INFORMATION, header);
    }

    public static void error(String header, String content){
        alert(Alert.AlertType.ERROR, header, content);
    }

    public static void error(String header){
        alert(Alert.AlertType.ERROR, header);
    }

}
