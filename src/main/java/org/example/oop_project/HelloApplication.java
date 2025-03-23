package org.example.oop_project;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoadingView.fxml"))));
        stage.show();


        Task<Scene> loadMainSceneTask = new Task<>() {
            @Override
            protected Scene call() throws Exception {

                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/main.fxml"));
                return new Scene(fxmlLoader.load());
            }
        };


        loadMainSceneTask.setOnSucceeded(event -> {
            Scene value = loadMainSceneTask.getValue();

            stage.setTitle("Supermarket FX");
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
    }
}