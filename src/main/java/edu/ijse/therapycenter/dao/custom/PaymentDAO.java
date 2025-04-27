package edu.ijse.therapycenter.dao.custom;

import edu.ijse.therapycenter.dao.CrudDAO;
import edu.ijse.therapycenter.entity.Payment;

import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment,String> {
    double calculateBalance(double fee, double amount);

    List<String> seacrh(String searchText) throws Exception;

    String getIdName(String name) throws Exception;

    List<String> getSessionID(String name) throws Exception;
}
