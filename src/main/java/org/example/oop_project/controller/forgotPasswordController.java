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

import javax.swing.*;
import java.io.IOException;

public class forgotPasswordController {

    private AnchorPane anchorPane;
    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtuserName;

    @FXML
    private TextField txtuseroption;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private PasswordField txtchoicepassword;

    public void initialize(){
        txtemail.requestFocus();
    }

    @FXML
    void O_Action_UserName(ActionEvent event) {
        txtuseroption.requestFocus();
    }

    @FXML
    void O_Action_email(ActionEvent event) {
        txtuserName.requestFocus();
    }

    @FXML
    void O_Action_option(ActionEvent event) {
        txtpassword.requestFocus();
    }


    @FXML
    void btn_OK(ActionEvent event) {
        new Thread(() -> {
            // Swing EDT එකට invoke කරන්න SwingUtilities භාවිතා කරනවා
            javax.swing.SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "PALAYAN GIHIN NIDAGANIN - Thread එකකින්!", "Me oii", JOptionPane.ERROR_MESSAGE);
            });
        }).start();

        clearPage();

    }

    private void clearPage() {
        txtemail.clear();
        txtuserName.clear();
        txtuseroption.clear();
        txtpassword.clear();
        txtchoicepassword.clear();
        txtuserName.requestFocus();

    }

    @FXML
    void onActionchoicepassword(ActionEvent event) {
            btn_OK(event);
    }

    @FXML
    void onActionpassword(ActionEvent event) {
        txtchoicepassword.requestFocus();
    }

    public void btnlogout(ActionEvent event) throws IOException {
        anchorPane = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("forgot password page");
    }
}
