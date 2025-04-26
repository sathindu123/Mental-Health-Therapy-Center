package edu.ijse.therapycenter.dao.custom;

import edu.ijse.therapycenter.dao.CrudDAO;
import edu.ijse.therapycenter.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.query.Query;

public interface TherapySessionDAO extends CrudDAO<TherapySession,String> {
    double getTherapyProgramFee(String id, Session session) ;
}
