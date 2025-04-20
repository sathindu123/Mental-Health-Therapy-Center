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
        USER
    }

    public SuperDAO getDAO(DAOType type) {
        return switch (type) {
            case USER -> new UserDAOImpl();
        };
    }

}
