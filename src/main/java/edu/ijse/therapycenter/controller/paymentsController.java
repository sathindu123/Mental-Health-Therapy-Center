package edu.ijse.therapycenter.controller;

import edu.ijse.therapycenter.bo.BOFactory;
import edu.ijse.therapycenter.bo.custom.impl.PaymentBOImpl;
import edu.ijse.therapycenter.bo.custom.impl.PaymentSessionBOImpl;
import edu.ijse.therapycenter.dto.PatientDTO;
import edu.ijse.therapycenter.dto.PaymentDTO;
import edu.ijse.therapycenter.dto.TherapySessionDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class paymentsController extends DashbordController{

    @FXML
    private Labeled blance;

    @FXML
    private TextField txtSearchId;

    @FXML
    private Button btnProcess;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<PaymentDTO, String> colAmount;

    @FXML
    private TableColumn<PaymentDTO, String> colDate;

    @FXML
    private TableColumn<PaymentDTO, String> colPatient;

    @FXML
    private TableColumn<PaymentDTO, String> colPaymentId;

    @FXML
    private TableColumn<PaymentDTO, String> colSession;

    @FXML
    private TableColumn<PaymentDTO, String> colStatus;

    @FXML
    private DatePicker lblDate;

    @FXML
    private TextField lblPatient;

    @FXML
    private TextField lblAmount;

    @FXML
    private Label lblPaymentId;

    @FXML
    private TableView<PaymentDTO> tblPayments;



    @FXML
    private Label lblSession;


    private String id;
    private final PaymentBOImpl paymentBO = (PaymentBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    private final PaymentSessionBOImpl paymentSessionBO = (PaymentSessionBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT_SESSION);

    @FXML
    void paymentSelectOnAction(MouseEvent event) {
        PaymentDTO selectedPayment = tblPayments.getSelectionModel().getSelectedItem();
        if (selectedPayment != null) {
            lblPaymentId.setText(selectedPayment.getId());
            lblAmount.setText(String.valueOf(selectedPayment.getAmount()));
            lblDate.setValue(LocalDate.parse(String.valueOf(selectedPayment.getDate())));
            lblSession.setText(selectedPayment.getTherapySession().getId());
            lblPatient.setText(selectedPayment.getPatient().getId());
        }
    }

    @FXML
    void processPayment(ActionEvent event) {
        try {
            String paymentId = lblPaymentId.getText();
            String sessionId = lblSession.getText();
            String date = lblDate.getValue().toString();
            String amount = lblAmount.getText();
            String patientId = lblPatient.getText();

            // Validate inputs
            if (amount.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please enter the amount.").show();
                return;
            }
            if (patientId == null || patientId.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please select a patient.").show();
                return;
            }
            if (sessionId == null || sessionId.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please select a session.").show();
                return;
            }

            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId(paymentId);
            paymentDTO.setAmount(Double.parseDouble(amount));
            paymentDTO.setDate(date);
            paymentDTO.setStatus("Paid");

            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setId(patientId);
            paymentDTO.setPatient(patientDTO);

            TherapySessionDTO therapySessionDTO = new TherapySessionDTO();
            therapySessionDTO.setId(sessionId);
            paymentDTO.setTherapySession(therapySessionDTO); // TherapySession set කරනවා
            therapySessionDTO.setStatus("Completed");

            paymentSessionBO.updateSession(therapySessionDTO, paymentDTO);
            loadPatientTable();

            new Alert(Alert.AlertType.INFORMATION, "Payment successful!").show();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid amount format. Please enter a valid number.").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error processing payment: " + e.getMessage()).show();
        }
    }
    @FXML
    void resetForm(ActionEvent event) {
        lblPaymentId.setText("");
        lblAmount.setText("");
        lblSession.setText("");
        lblPatient.setText("");
        lblDate.setValue(null);

    }

    @Override
    public void initialize() {
        this.id = String.valueOf(paymentBO.getLastPK().orElse("Error"));
        lblPaymentId.setText(id);

        colPaymentId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAmount())));
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDate())));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        colPatient.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getId()));
        colSession.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTherapySession().getId()));
        loadPatientTable();
    }

    private void loadPatientTable() {
        List<PaymentDTO> paymentList = paymentBO.getAll();
        ObservableList<PaymentDTO> paymentTMS = FXCollections.observableArrayList(paymentList);
        tblPayments.setItems(paymentTMS);
    }

    public void ActionSearch(ActionEvent event) {
        String name = txtSearchId.getText();

    }
}
