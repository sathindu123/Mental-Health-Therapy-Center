package org.example.oop_project.dao;

import org.example.oop_project.dao.custom.impl.UserManageDAOimpl;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory() {
    }

    public static DaoFactory getInstance(){
        return daoFactory == null ? (daoFactory = new DaoFactory()) : daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DaoTypes daoTypes) {
        return switch (daoTypes) {
            case USER -> (T) new UserManageDAOimpl();
            default -> null;
        };
    }

}
