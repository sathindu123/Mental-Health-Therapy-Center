package edu.ijse.therapycenter.bo.custom;

import edu.ijse.therapycenter.bo.SuperBO;
import edu.ijse.therapycenter.dto.PaymentDTO;
import edu.ijse.therapycenter.dto.TherapySessionDTO;

public interface PaymentSessionBO extends SuperBO {
    void saveSession(TherapySessionDTO therapySessionDTO, PaymentDTO paymentDTO);

}