package org.example.oop_project.bo.custom.impl;

import org.example.oop_project.bo.custom.UserManageBO;
import org.example.oop_project.dao.DaoFactory;
import org.example.oop_project.dao.DaoTypes;
import org.example.oop_project.dao.custom.UserManageDAO;

public class UserManageBOimpl implements UserManageBO {
    UserManageDAO userManageDAO = DaoFactory.getInstance().getDAO(DaoTypes.USER);



    public boolean cheack(String userName, String password) {
//        boolean username = userManageDAO.cheackUserName(userName,password);
//        return username;
        return true;
    }
}
