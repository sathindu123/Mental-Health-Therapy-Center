package org.example.oop_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashbordController {
    private AnchorPane anchorPane;

    public void btnlogout(ActionEvent event) throws IOException {
        anchorPane = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Serenity Mental Health Therapy Center");
    }

    public void btnsignIn(MouseEvent event) throws IOException {
        anchorPane = FXMLLoader.load(getClass().getResource("/view/signupPage.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Sign In Page");
    }
}
