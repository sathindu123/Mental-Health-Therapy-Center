package edu.ijse.therapycenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashbordController extends LogInController{
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane PaymentAncorPane;

    @FXML
    private AnchorPane TherapistAncorPane;

    @FXML
    private AnchorPane PatientAncorPane;

    @FXML
    private AnchorPane ProgrameAncorPane;

    @FXML
    private AnchorPane TimeAncorPane;


    public void initialize(){
        String role = LogInController.getRoleID();
        if (!(role.equals("Admin"))) {
            TherapistAncorPane.setDisable(true);
            ProgrameAncorPane.setDisable(true);

        }
        else {
            TimeAncorPane.setDisable(true);
            PatientAncorPane.setDisable(true);
            PaymentAncorPane.setDisable(true);
        }

    }

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

    public void btnPatientManage(MouseEvent event) throws IOException {
        anchorPane = FXMLLoader.load(getClass().getResource("/view/PatientManage.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Serenity Mental Health Therapy Center");
    }

    public void btnTherapistMAnage(MouseEvent event) throws IOException {
        anchorPane = FXMLLoader.load(getClass().getResource("/view/TherapistManage.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Serenity Mental Health Therapy Center");
    }

    public void btnPayments(MouseEvent event) throws IOException {
        anchorPane = FXMLLoader.load(getClass().getResource("/view/payments.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Serenity Mental Health Therapy Center");
    }

    public void btnManagePrograme(MouseEvent event) throws IOException {
        anchorPane = FXMLLoader.load(getClass().getResource("/view/ProgrameManage.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Serenity Mental Health Therapy Center");
    }

    public void btnSessionScheduling(MouseEvent event) throws IOException {
        anchorPane = FXMLLoader.load(getClass().getResource("/view/SessionScheduling.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Serenity Mental Health Therapy Center");
    }

    public void backOnaction(ActionEvent event) throws IOException {
        anchorPane = FXMLLoader.load(getClass().getResource("/view/Dashbord.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Serenity Mental Health Therapy Center");
    }
}
