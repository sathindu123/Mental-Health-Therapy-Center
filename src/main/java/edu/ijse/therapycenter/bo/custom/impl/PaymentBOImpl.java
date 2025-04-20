package edu.ijse.therapycenter.bo.custom.impl;

import edu.ijse.therapycenter.bo.custom.PaymentBO;
import edu.ijse.therapycenter.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PaymentBOImpl implements PaymentBO {

    @Override
    public boolean save(PaymentDTO payment) {
        return false;
    }

    @Override
    public boolean update(PaymentDTO payment) {
        return false;
    }

    @Override
    public boolean deleteByPK(String pk) throws Exception {
        return false;
    }

    @Override
    public List<PaymentDTO> getAll() {
        return List.of();
    }

    @Override
    public Optional<PaymentDTO> findByPK(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        return Optional.empty();
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
