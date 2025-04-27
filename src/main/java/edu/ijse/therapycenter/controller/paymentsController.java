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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class paymentsController extends DashbordController{

    @FXML
    private ListView<String> ListView;
    @FXML
    private ListView<String> ListVi;
    @FXML
    private TextField lblSession;

    @FXML
    private Label balance;

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

            double balanc = 0;
            balanc = paymentSessionBO.updateSession(therapySessionDTO, paymentDTO);
            balance.setText(""+balanc);
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
        ListView.setVisible(false);
        ListVi.setVisible(false);
        this.id = String.valueOf(paymentBO.getLastPK().orElse("Error"));
        lblPaymentId.setText(id);

        colPaymentId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAmount())));
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDate())));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        colPatient.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getId()));
        colSession.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTherapySession().getId()));
        loadPatientTable();

        txtSearchId.setOnKeyReleased(this::onKeyReleased);


        ListView.setVisible(false);

        ListView.setOnMouseClicked(event -> {
            String selected = ListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                txtSearchId.setText(selected);
                ListView.getItems().clear();
                ListView.setVisible(false);
            }
        });


        txtSearchId.setOnKeyPressed(this::handleKeyPress);
    }

    private void loadPatientTable() {
        List<PaymentDTO> paymentList = paymentBO.getAll();
        ObservableList<PaymentDTO> paymentTMS = FXCollections.observableArrayList(paymentList);
        tblPayments.setItems(paymentTMS);
    }

    public void ActionSearch(ActionEvent event) {
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.ENTER) {
            return;
        }

        String searchText = txtSearchId.getText();
        if (searchText.isEmpty()) {
            ListView.getItems().clear();
            ListView.setVisible(false);
            return;
        }

        List<String> suggestions = new ArrayList<>();
        try {
            suggestions = paymentBO.searchListItam(searchText);
            System.out.println(suggestions+"hi lassan lamo");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!suggestions.isEmpty()) {
            ListView.getItems().setAll(suggestions);
            ListView.setVisible(true);
            // Select the first item by default
            ListView.getSelectionModel().selectFirst();
        } else {
            ListView.getItems().clear();
            ListView.setVisible(false);
        }
    }

    private void handleKeyPress(KeyEvent keyEvent) {
        if (!ListView.isVisible() || ListView.getItems().isEmpty()) {
            return;
        }

        int currentIndex = ListView.getSelectionModel().getSelectedIndex();

        switch (keyEvent.getCode()) {
            case DOWN:
                // Move selection down
                if (currentIndex < ListView.getItems().size() - 1) {
                    ListView.getSelectionModel().select(currentIndex + 1);
                    ListView.scrollTo(currentIndex + 1);
                }
                keyEvent.consume();
                break;

            case UP:
                // Move selection up
                if (currentIndex > 0) {
                    ListView.getSelectionModel().select(currentIndex - 1);
                    ListView.scrollTo(currentIndex - 1);
                }
                keyEvent.consume();
                break;

            case ENTER:
                // Select the item and populate the TextField
                String selected = ListView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    txtSearchId.setText(selected);
                    ListView.getItems().clear();
                    ListView.setVisible(false);
                }
                keyEvent.consume();
                try {
                    tblLoad();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;

            case ESCAPE:
                // Hide the ListView when Escape is pressed
                ListView.getItems().clear();
                ListView.setVisible(false);
                keyEvent.consume();
                break;
        }
    }

    private void tblLoad() throws Exception {
        String name = txtSearchId.getText();
        name = paymentBO.getIdName(name);
        lblPatient.setText(name);

        List<String> suggestions = new ArrayList<>();
        try {
            suggestions = paymentBO.getSessionId(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ObservableList<String> DL = FXCollections.observableArrayList();
        DL.addAll(suggestions);
        ListVi.setItems(DL);


        if (ListVi.getItems().isEmpty() || lblSession.getText() == null || lblSession.getText().isEmpty()) {
            ListVi.setVisible(true);
        }



        method();
        String ss = lblSession.getText();
        if (!(ss == null || ss.isEmpty())) {
            ListVi.setVisible(false);
        }
    }

    private void method() {
        ListVi.setOnMouseClicked(event -> {
            String selected = ListVi.getSelectionModel().getSelectedItem();
            if (selected != null) {
                lblSession.setText(selected);
                ListVi.getItems().clear(); // Clear items
                ListVi.setVisible(false); // Invisible කරනවා
            }
        });
    }


}
