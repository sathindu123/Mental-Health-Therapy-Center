package edu.ijse.therapycenter.dao.custom.impl;

import edu.ijse.therapycenter.config.FactoryConfiguration;
import edu.ijse.therapycenter.dao.custom.PaymentDAO;
import edu.ijse.therapycenter.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PaymentDAOImpl implements PaymentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Payment payment) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(payment);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean update(Payment payment) {
        return false;
    }

    @Override
    public boolean deleteByPK(String pk) throws Exception {
        return false;
    }

    @Override
    public List<Payment> getAll() {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return session.createQuery("FROM Payment ", Payment.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Payment> findByPK(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        Session session = null;
        try {
            session = factoryConfiguration.getSession();
            Long lastPk = session
                    .createQuery("SELECT p.id FROM Payment p ORDER BY p.id DESC", Long.class)
                    .setMaxResults(1)
                    .uniqueResult();

            Long newPk = (lastPk != null) ? lastPk + 1 : 1;

            System.out.println(newPk);

            return Optional.of(String.valueOf(newPk));

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public double calculateBalance(double fee, double amount) {
        return fee - amount;
    }

    @Override
    public List<String> seacrh(String searchText) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            // JPQL query එක ලියනවා
            String jpql = "SELECT p.name FROM TherapySession ts JOIN ts.patient p WHERE p.name LIKE :searchText";
            List<String> suggestions = session.createQuery(jpql, String.class)
                    .setParameter("searchText", searchText + "%")
                    .getResultList();

            return suggestions;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while searching patients in therapy sessions: " + e.getMessage(), e);
        }
    }

    @Override
    public String getIdName(String name) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            // JPQL query එක ලියනවා
            String jpql = "SELECT p.id FROM Patient p WHERE p.name = :name";
            List<String> result = session.createQuery(jpql, String.class)
                    .setParameter("name", name)
                    .setMaxResults(1) // එක patient එකක් විතරක් ගන්න
                    .getResultList();

            if (result.isEmpty()) {
                throw new Exception("Patient not found for name: " + name);
            }

            return result.get(0); // ID එක return කරනවා
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while searching patient ID by name: " + e.getMessage(), e);
        }

    }

    @Override
    public List<String> getSessionID(String name) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {

            String jpql = "SELECT ts.id FROM TherapySession ts WHERE ts.patient.id = :patientId";
            List<String> sessionIds = session.createQuery(jpql, String.class)
                    .setParameter("patientId", name)
                    .getResultList();

            if (sessionIds.isEmpty()) {
                throw new Exception("No therapy sessions found for patient ID: " + name);
            }

            return sessionIds;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while retrieving session IDs for patient ID: " + name, e);
        }

    }
}
