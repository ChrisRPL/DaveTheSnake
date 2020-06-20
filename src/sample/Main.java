package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("res/fxml/start_page.fxml"));
        Scene startPage = new Scene(root);
        startPage.getStylesheets().add(getClass().getResource("/sample/res/css/start_page.css").toExternalForm());
        primaryStage.setScene(startPage);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
