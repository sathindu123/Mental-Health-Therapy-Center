package org.example.oop_project.dao.custom.impl;

import org.example.oop_project.config.FactoryConfiguration;
import org.example.oop_project.dao.custom.UserManageDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;




public class UserManageDAOimpl implements UserManageDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    public boolean cheackUserName(String userName, String password) {
        boolean isUserExists = false;

        try (Session session = factoryConfiguration.getSession()) {
            String hql = "SELECT COUNT(u) FROM User u WHERE u.userName = :userName AND u.password = :password";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("userName", userName);
            query.setParameter("password", password);

            Long count = query.uniqueResult();
            isUserExists = (count != null && count > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUserExists;
    }
}
