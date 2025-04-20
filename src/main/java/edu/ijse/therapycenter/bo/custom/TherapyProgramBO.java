package edu.ijse.therapycenter.bo.custom;

import edu.ijse.therapycenter.bo.SuperBO;
import edu.ijse.therapycenter.dto.TherapyProgramDTO;
import edu.ijse.therapycenter.entity.TherapyProgram;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TherapyProgramBO extends SuperBO {

    boolean save(TherapyProgramDTO therapyProgram);
    boolean update(TherapyProgramDTO therapyProgram);
    boolean deleteByPK(String pk) throws Exception;
    List<TherapyProgramDTO> getAll();
    Optional<TherapyProgramDTO> findByPK(String pk);
    Optional<String> getLastPK();
    boolean exist(String id) throws SQLException, ClassNotFoundException;

}
