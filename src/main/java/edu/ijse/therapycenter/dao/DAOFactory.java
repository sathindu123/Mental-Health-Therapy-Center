package edu.ijse.therapycenter.dao;

import edu.ijse.therapycenter.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;
    private DAOFactory() {

    }

    public static DAOFactory getInstance() {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOType {
        PATIENT,
        THERAPIST,
        PAYMENT,
        THERAPY_PROGRAM,
        THERAPY_SESSION,
        USER
    }

    public SuperDAO getDAO(DAOType type) {
        return switch (type) {
            case PATIENT -> new PatientDAOImpl();
            case USER -> new UserDAOImpl();
            case THERAPIST -> new TherapistDAOImpl();
            case THERAPY_PROGRAM -> new TherapyProgramDAOImpl();
            case THERAPY_SESSION -> new TherapySessionDAOImpl();
            case PAYMENT -> new PaymentDAOImpl();
        };
    }

}
