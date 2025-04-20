package edu.ijse.therapycenter;

import edu.ijse.therapycenter.config.FactoryConfiguration;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import edu.ijse.therapycenter.entity.*;

import java.io.IOException;
import java.time.LocalDate;

public class Initializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoadingView.fxml"))));
        stage.show();


        Task<Scene> loadMainSceneTask = new Task<>() {
            @Override
            protected Scene call() throws Exception {


                FXMLLoader fxmlLoader = new FXMLLoader(Initializer.class.getResource("/view/main.fxml"));
                return new Scene(fxmlLoader.load());
            }
        };


        loadMainSceneTask.setOnSucceeded(event -> {
            Scene value = loadMainSceneTask.getValue();

            stage.setTitle("Serenity Mental Health Therapy Center");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app_logo.png")));

//            stage.setMaximized(true);


            stage.setScene(value);
            stage.centerOnScreen();
        });


        loadMainSceneTask.setOnFailed(event -> {
            System.err.println("Failed to load the main layout.");
        });


        new Thread(loadMainSceneTask).start();
    }

    public static void main(String[] args) {
        launch();

//
    }
}