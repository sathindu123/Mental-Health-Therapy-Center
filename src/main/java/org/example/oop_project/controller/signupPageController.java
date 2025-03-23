package org.example.oop_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class signupPageController {
    private AnchorPane anchorPane;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtuserName;

    @FXML
    private PasswordField txtchoicepassword;

    @FXML
    private PasswordField txtpassword;

    @FXML
    void O_Action_UserName(ActionEvent event) {
        txtpassword.requestFocus();
    }

    @FXML
    void O_Action_email(ActionEvent event) {
        txtuserName.requestFocus();
    }

    @FXML
    void btnsignIn(ActionEvent event) {
         String email = txtemail.getText();
         String userName = txtuserName.getText();
         String password = txtpassword.getText();

    }

    @FXML
    void onActionchoicepassword(ActionEvent event) {
        btnsignIn(event);
    }

    @FXML
    void onActionpassword(ActionEvent event) {
        txtchoicepassword.requestFocus();
    }

    public void btnHomePage(ActionEvent event) throws IOException {
        anchorPane = FXMLLoader.load(getClass().getResource("/view/Dashbord.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Serenity Mental Health Therapy Center");
    }
}
