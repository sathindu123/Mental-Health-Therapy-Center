package edu.ijse.therapycenter.bo.custom.impl;

import edu.ijse.therapycenter.bo.custom.PaymentSessionBO;
import edu.ijse.therapycenter.config.FactoryConfiguration;
import edu.ijse.therapycenter.dao.DAOFactory;
import edu.ijse.therapycenter.dao.custom.impl.PaymentDAOImpl;
import edu.ijse.therapycenter.dao.custom.impl.TherapySessionDAOImpl;
import edu.ijse.therapycenter.dto.PaymentDTO;
import edu.ijse.therapycenter.dto.TherapySessionDTO;
import edu.ijse.therapycenter.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PaymentSessionBOImpl implements PaymentSessionBO {

    private final PaymentDAOImpl paymentDAO = (PaymentDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    private final TherapySessionDAOImpl therapySessionDAO = (TherapySessionDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPY_SESSION);

    @Override
    public void saveSession(TherapySessionDTO therapySessionDTO, PaymentDTO paymentDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            TherapySession therapySession = therapyToEntity(therapySessionDTO);
            Payment payment = paymentToEntity(paymentDTO);

            boolean therapySaved = therapySessionDAO.save(therapySession);
            if (!therapySaved) {
                transaction.rollback();
                return;
            }

            boolean paymentSaved = paymentDAO.save(payment);
            if (!paymentSaved) {
                transaction.rollback();
                return;
            }

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }





    public static TherapySession therapyToEntity(TherapySessionDTO dto) {
        if (dto == null) return null;

        TherapySession entity = new TherapySession();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setTime(dto.getTime());
        entity.setStatus(dto.getStatus());

        entity.setTherapist(dto.getTherapist() != null ? new Therapist(
                dto.getTherapist().getId(),
                dto.getTherapist().getName(),
                dto.getTherapist().getSpecialization(),
                null
        ) : null);

        entity.setPatient(dto.getPatient() != null ? new Patient(
                dto.getPatient().getId(),
                dto.getPatient().getName(),
                dto.getPatient().getContactInfo(),
                dto.getPatient().getGender(),
                dto.getPatient().getBirthDate(),
                null
        ) : null);

        entity.setTherapyProgram(dto.getTherapyProgram() != null ? new TherapyProgram(
                dto.getTherapyProgram().getProgramId(),
                dto.getTherapyProgram().getName(),
                dto.getTherapyProgram().getDuration(),
                dto.getTherapyProgram().getFee(),
                null
        ) : null);

        return entity;
    }

    public static Payment paymentToEntity(PaymentDTO dto) {
        if (dto == null) return null;

        Patient patient = new Patient();
        patient.setId(dto.getPatient().getId());

        TherapySession session = new TherapySession();
        session.setId(dto.getTherapySession().getId());

        return new Payment(
                dto.getId(),
                dto.getAmount(),
                dto.getDate(),
                dto.getStatus(),
                patient,
                session
        );
    }

    private TherapySession therapyToEntity1(TherapySessionDTO dto, Session session) throws Exception {
        TherapySession therapySession = session.get(TherapySession.class, dto.getId());
        if (therapySession == null) {
            throw new Exception("TherapySession not found for ID: " + dto.getId());
        }
        therapySession.setStatus(dto.getStatus());
        return therapySession;
    }
    private Payment paymentToEntity1(PaymentDTO dto, Session session) throws Exception {
        Patient patient = null;
        if (dto.getPatient() != null) {
            patient = session.get(Patient.class, dto.getPatient().getId());
            if (patient == null) {
                throw new Exception("Patient not found for ID: " + dto.getPatient().getId());
            }
        } else {
            throw new Exception("Patient cannot be null in PaymentDTO");
        }

        TherapySession therapySession = null;
        if (dto.getTherapySession() != null) {
            therapySession = session.get(TherapySession.class, dto.getTherapySession().getId());
            if (therapySession == null) {
                throw new Exception("TherapySession not found for ID: " + dto.getTherapySession().getId());
            }
        } else {
            throw new Exception("TherapySession cannot be null in PaymentDTO");
        }

        return new Payment(
                dto.getId(),
                dto.getAmount(),
                dto.getDate(),
                dto.getStatus(),
                patient,
                therapySession
        );
    }


    public double updateSession(TherapySessionDTO therapySessionDTO, PaymentDTO paymentDTO) throws Exception {
        double balance = 0;
        Session session = null;
        try {
            session = FactoryConfiguration.getInstance().getSession();
            TherapySession therapySession = therapyToEntity1(therapySessionDTO, session);
            Payment payment = paymentToEntity1(paymentDTO, session);

            double fee = therapySessionDAO.getTherapyProgramFee(therapySessionDTO.getId(), session);
            if (fee == 0.0) {
                throw new Exception("Therapy Program fee not found for session ID: " + therapySessionDTO.getId());
            }

            double amount = paymentDTO.getAmount();
            balance = paymentDAO.calculateBalance(fee, amount);
            if (balance < 0) {
                throw new Exception("Amount exceeds the therapy program fee. Balance cannot be negative.");
            }

            updateTherapySession(therapySession);
            savePayment(payment);

            System.out.println("Payment successful! Remaining balance: " + balance);
        } finally {
            if (session != null) {
                session.close();
                System.out.println("Session closed.");
            }
        }
        System.out.println("himoko"+balance);
        return balance;
    }

    private void updateTherapySession(TherapySession therapySession) throws Exception {
        boolean therapySaved = therapySessionDAO.update(therapySession);
        if (!therapySaved) {
            throw new Exception("Failed to update TherapySession for ID: " + therapySession.getId());
        }
    }

    private void savePayment(Payment payment) throws Exception {
        boolean paymentSaved = paymentDAO.save(payment);
        if (!paymentSaved) {
            throw new Exception("Failed to save Payment for ID: " + payment.getId());
        }
    }
}
