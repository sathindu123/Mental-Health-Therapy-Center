package org.example.oop_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.oop_project.bo.custom.UserManageBO;
import org.example.oop_project.bo.custom.impl.UserManageBOimpl;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class maincontroller implements Initializable {
    private UserManageBO userManageBO;
    private AnchorPane anchorPane;
    @FXML
    private TextField txtuserName;

    @FXML
    private TextField txtPassword;


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
        String UserName = txtuserName.getText();

        try {
            boolean cheack = userManageBO.cheack(UserName,password);
            System.out.println(cheack);
            if(cheack){
                anchorPane = FXMLLoader.load(getClass().getResource("/view/Dashbord.fxml"));
                Scene scene = new Scene(anchorPane);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.setTitle("forgot password page");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void clearPage() {
        txtuserName.clear();
        txtPassword.clear();
        txtuserName.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userManageBO = new UserManageBOimpl();
    }
}
