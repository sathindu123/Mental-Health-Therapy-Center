package org.example.oop_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;


public class maincontroller {
    private AnchorPane anchorPane;
    @FXML
    private TextField txtuserName;

    @FXML
    private TextField txtPassword;

    public void initialize(){
        txtuserName.requestFocus();
    }

    @FXML
    void O_Actio0n_Password(ActionEvent event) throws IOException {
        loginButton(event);
    }

    @FXML
    void O_Action_UserName(ActionEvent event) {
        txtPassword.requestFocus();

    }

    @FXML
    void O_mouse_Forgot(MouseEvent event) throws IOException {
        anchorPane = FXMLLoader.load(getClass().getResource("/view/forgotPasswordPage.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("forgot password page");
    }

    @FXML
    void loginButton(ActionEvent event) throws IOException {
        String password = txtPassword.getText();
        String USerName = txtuserName.getText();

        anchorPane = FXMLLoader.load(getClass().getResource("/view/Dashbord.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("forgot password page");
    }

    private void clearPage() {
        txtuserName.clear();
        txtPassword.clear();
        txtuserName.requestFocus();
    }
}
