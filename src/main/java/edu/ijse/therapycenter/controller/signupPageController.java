package edu.ijse.therapycenter.controller;

import edu.ijse.therapycenter.bo.BOFactory;
import edu.ijse.therapycenter.bo.custom.impl.UserBOImpl;
import edu.ijse.therapycenter.dto.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class signupPageController implements Initializable {
    private final UserBOImpl userBO = (UserBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    private AnchorPane anchorPane;

    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private ChoiceBox<String> choiceRole;

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
         String userName = txtuserName.getText();
         String password = txtpassword.getText();
         String choicePassword = txtchoicepassword.getText();
        String role = choiceRole.getValue();

        String lastId = userBO.getLastPK().orElse("U001");



        if(userName.isEmpty() || password.isEmpty() || role.isEmpty() || choicePassword.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "All filed must be not compited").show();
            return;
        }

        if(password.length() < 8){
            new Alert(Alert.AlertType.INFORMATION, "Password must be at least 8 characters").show();

            return;
        }

        if(!password.equals(choicePassword)){
            new Alert(Alert.AlertType.INFORMATION, "Password does not match").show();
            return;
        }

        if(userBO.cheackUser(userName)){
            new Alert(Alert.AlertType.INFORMATION, "User already exist").show();
            return;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(lastId);
        userDTO.setUsername(userName);
        userDTO.setPassword(password);
        userDTO.setRole(role);

        boolean result = userBO.save(userDTO);

        if(result){
            new Alert(Alert.AlertType.INFORMATION, "Succsessfully").show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("User sign up failed");
            alert.show();
            return;
        }

    }

    @FXML
    void onActionchoicepassword(ActionEvent event) {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceRole.getItems().addAll("Admin", "Receptionist");
    }
}
