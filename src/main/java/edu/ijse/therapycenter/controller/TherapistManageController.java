package edu.ijse.therapycenter.controller;

import edu.ijse.therapycenter.bo.BOFactory;
import edu.ijse.therapycenter.bo.custom.impl.TherapistBOImpl;
import edu.ijse.therapycenter.dto.TherapistDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TherapistManageController extends DashbordController{
    private AnchorPane anchorPane;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TherapistDTO, String> colId;

    @FXML
    private TableColumn<TherapistDTO, String> colName;

    @FXML
    private TableColumn<TherapistDTO, String> colSpecialization;

    @FXML
    private Label errorMessage;

    @FXML
    private Label lblTherapistId;

    @FXML
    private TableView<TherapistDTO> tblTherapists;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSpecialization;

    private String id;

    private final TherapistBOImpl therapistBO = (TherapistBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);

    @FXML
    void addTherapist(ActionEvent event) {
        String id = this.id;
        String name = txtName.getText();
        String specialization = txtSpecialization.getText();

        if(name.isEmpty() || specialization.isEmpty()){
            errorMessage.setText("Please fill all the fields");
            return;
        }

        TherapistDTO therapistDTO = new TherapistDTO();
        therapistDTO.setId(id);
        therapistDTO.setName(name);
        therapistDTO.setSpecialization(specialization);

        boolean isAdded = therapistBO.save(therapistDTO);

        if(isAdded){
            txtName.clear();
            txtSpecialization.clear();
            this.id = String.valueOf(therapistBO.getLastPK().orElse("Error"));
        }else{
            errorMessage.setText("Failed to add therapist");
        }
    }

    @FXML
    void deleteTherapist(ActionEvent event) throws Exception {
        String id = lblTherapistId.getText();
        boolean isDeleted = therapistBO.deleteByPK(id);

        if (isDeleted) {
            txtName.clear();
            txtSpecialization.clear();
            txtSpecialization.setDisable(false);
            lblTherapistId.setText(id);
            loadTherapistTable();
        } else {

        }
    }

    @FXML
    void resetForm(ActionEvent event) {
        txtName.clear();
        txtSpecialization.clear();
        txtSpecialization.setDisable(false);
        lblTherapistId.setText(id);
    }

    @FXML
    void therapistSelectOnAction(MouseEvent event) {
        TherapistDTO selectedTherapist = tblTherapists.getSelectionModel().getSelectedItem();
        if (selectedTherapist != null) {
            lblTherapistId.setText(selectedTherapist.getId());
            txtName.setText(selectedTherapist.getName());
            txtSpecialization.setText(selectedTherapist.getSpecialization());
            txtSpecialization.setDisable(true);
        }
    }

    @FXML
    void updateTherapist(ActionEvent event) {
        String id = lblTherapistId.getText();
        String name = txtName.getText();
        txtSpecialization.setDisable(true);
        String specialization = txtSpecialization.getText();

        if(name.isEmpty() || specialization.isEmpty()){
            errorMessage.setText("Please fill all the fields");
            return;
        }

        TherapistDTO therapistDTO = new TherapistDTO();
        therapistDTO.setId(id);
        therapistDTO.setName(name);
        therapistDTO.setSpecialization(specialization);

        boolean isUpdated = therapistBO.update(therapistDTO);

        if(isUpdated){
            txtName.clear();
            txtSpecialization.clear();
            txtSpecialization.setDisable(false);
            loadTherapistTable();
        }else{
            errorMessage.setText("Failed to update therapist");
        }
    }


    public void initialize() {
        this.id = String.valueOf(therapistBO.getLastPK().orElse("Error"));
        lblTherapistId.setText(id);

        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colSpecialization.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpecialization()));

        loadTherapistTable();
    }

    private void loadTherapistTable() {
        List<TherapistDTO> therapistList = therapistBO.getAll();
        ObservableList<TherapistDTO> therapistTMS = FXCollections.observableArrayList(therapistList);
        tblTherapists.setItems(therapistTMS);
    }
}
