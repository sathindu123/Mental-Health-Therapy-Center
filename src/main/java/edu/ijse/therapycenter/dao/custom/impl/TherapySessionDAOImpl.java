package edu.ijse.therapycenter.dao.custom.impl;

import edu.ijse.therapycenter.config.FactoryConfiguration;
import edu.ijse.therapycenter.dao.custom.TherapySessionDAO;
import edu.ijse.therapycenter.entity.Patient;
import edu.ijse.therapycenter.entity.Therapist;
import edu.ijse.therapycenter.entity.TherapyProgram;
import edu.ijse.therapycenter.entity.TherapySession;
import org.hibernate.PessimisticLockException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TherapySessionDAOImpl implements TherapySessionDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(TherapySession therapySession) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            Therapist therapist = session.get(Therapist.class, therapySession.getTherapist().getId());
            Patient patient = session.get(Patient.class, therapySession.getPatient().getId());
            TherapyProgram program = session.get(TherapyProgram.class, therapySession.getTherapyProgram().getProgramId());

            if (therapist == null || patient == null || program == null) {
                System.out.println("Referenced entities do not exist.");
                transaction.rollback();
                return false;
            }

            session.createNativeQuery(
                            "INSERT INTO therapy_sessions (id, date, time, status, therapist_id, patient_id, program_id) " +
                                    "VALUES (:id, :date, :time, :status, :therapistId, :patientId, :programId)")
                    .setParameter("id", therapySession.getId())
                    .setParameter("date", therapySession.getDate())
                    .setParameter("time", therapySession.getTime())
                    .setParameter("status", therapySession.getStatus())
                    .setParameter("therapistId", therapist.getId())
                    .setParameter("patientId", patient.getId())
                    .setParameter("programId", program.getProgramId())
                    .executeUpdate();

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(TherapySession therapySession) {
        int maxRetries = 3;
        int retryCount = 0;
        long retryDelay = 1000; // 1 second delay between retries

        while (retryCount < maxRetries) {
            Session session = null;
            Transaction transaction = null;
            try {
                session = FactoryConfiguration.getInstance().getSession();
                transaction = session.beginTransaction();

                System.out.println("Updating TherapySession with ID: " + therapySession.getId() + ", Status: " + therapySession.getStatus());

                int updatedRows = session.createNativeQuery(
                                "UPDATE therapy_sessions SET status = :status WHERE id = :id")
                        .setParameter("status", therapySession.getStatus())
                        .setParameter("id", therapySession.getId())
                        .executeUpdate();

                System.out.println("Rows updated: " + updatedRows);

                if (updatedRows == 0) {
                    System.out.println("No rows updated for TherapySession ID: " + therapySession.getId());
                    transaction.rollback();
                    return false;
                }

                transaction.commit();
                System.out.println("Transaction committed successfully.");
                return true;
            } catch (PessimisticLockException | jakarta.persistence.LockTimeoutException e) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    System.out.println("Max retries reached. Failed to update TherapySession due to lock timeout: " + e.getMessage());
                    throw e; // Re-throw the exception after max retries
                }
                System.out.println("Lock timeout occurred. Retrying... Attempt " + retryCount + " of " + maxRetries);
                if (transaction != null) {
                    transaction.rollback();
                }
                try {
                    Thread.sleep(retryDelay); // Wait before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } catch (Exception e) {
                if (transaction != null) {
                    System.out.println("Rolling back transaction due to error: " + e.getMessage());
                    transaction.rollback();
                }
                e.printStackTrace();
                return false;
            } finally {
                if (session != null) {
                    session.close();
                    System.out.println("Session closed.");
                }
            }
        }
        return false; // Should not reach here due to exception throw
    }

    @Override
    public boolean deleteByPK(String sessionId) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            TherapySession therapySession = session.get(TherapySession.class, sessionId);

            if (therapySession != null) {
                session.delete(therapySession);
                transaction.commit();
                return true;
            } else {
                System.out.println("TherapySession with ID " + sessionId + " not found.");
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<TherapySession> getAll() {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return session.createQuery("FROM TherapySession", TherapySession.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<TherapySession> findByPK(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        Session session = null;
        try {
            session = factoryConfiguration.getSession();
            Long lastPk = session
                    .createQuery("SELECT p.id FROM TherapySession p ORDER BY p.id DESC", Long.class)
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

    public double getTherapyProgramFee(String id, Session session) {
        String hql = "SELECT tp.fee FROM TherapySession ts JOIN ts.therapyProgram tp WHERE ts.id = :sessionId";
        Query<Double> query = session.createQuery(hql, Double.class);
        query.setParameter("sessionId", id);
        return query.uniqueResult() != null ? query.uniqueResult() : 0.0;
    }
}
