package edu.ijse.therapycenter.controller;

import edu.ijse.therapycenter.bo.BOFactory;
import edu.ijse.therapycenter.bo.custom.impl.UserBOImpl;
import edu.ijse.therapycenter.dto.UserDTO;
import edu.ijse.therapycenter.util.PasswordUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class LogInController {

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private Button btSignIn;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    private final UserBOImpl userBO = (UserBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    void navHomePage(ActionEvent event) throws IOException {
        String userName = txtEmail.getText();
        String password = txtPassword.getText();

        if(userName.isEmpty() || password.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "UserName OR Password not compited!!").show();
            return;
        }

        boolean result = userBO.cheackUser(userName);

        if(result){
            UserDTO userDTO = userBO.cheackPassword(userName);

            String role = userDTO.getRole();
            String hashedDTO = userDTO.getPassword();



            boolean isPasswordValid = PasswordUtils.verifyPassword(password, hashedDTO);

            if(!isPasswordValid){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid password");
                alert.show();
            }else {
                if(role.equals("Admin")){
                    Parent dashboard = FXMLLoader.load(getClass().getResource("/view/Dashbord.fxml"));
                    Scene scene = new Scene(dashboard);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    if (stage == null) {
                        System.out.println("Stage is null! Cannot navigate.");
                        return;
                    }
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.setResizable(false);
                    stage.show();
                }else if(role.equals("Receptionist")){
                    if (mainAnchor == null) {
                        System.out.println("mainAnchor is null! Cannot load dashboard.");
                        return;
                    }
                    mainAnchor.getChildren().clear();
                    Parent dashboard = FXMLLoader.load(getClass().getResource("/view/Dashbord.fxml"));
                    mainAnchor.getChildren().add(dashboard);}
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email");
            alert.showAndWait();
        }

    }

    @FXML
    void navSignUp(MouseEvent event) throws IOException {
        mainAnchor.getChildren().clear();
        mainAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/SignUp.fxml")));

    }

    public void O_Action_UserName(ActionEvent event) {
        txtPassword.requestFocus();
    }

    public void O_Actio0n_Password(ActionEvent event) throws IOException {
        navHomePage(event);
    }
}
