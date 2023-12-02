package pkoh.krokFinalProject.repository;

import pkoh.krokFinalProject.customExceptions.CandidateWithIdNotFoundException;
import pkoh.krokFinalProject.dataBase.DAO.CandidateStatusDAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Промежуточный уровень между DAO-классами и реализацией DAO-классов
 */
public class CandidateStatusRepository {
    private final CandidateStatusDAO candidateStatusDAO;

    public  CandidateStatusRepository(Connection connection) {
        candidateStatusDAO = new CandidateStatusDAO(connection);
    }
    public void addNewCandidateRegistrationStatus (int candidateID)  {
        try {
            candidateStatusDAO.addNewCandidateRegistrationStatus(candidateID);
        } catch (SQLException | CandidateWithIdNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void setCandidateRegistrationStatus(int candidateId, boolean value)  {
        try {
            candidateStatusDAO.setCandidateRegistrationStatus(candidateId, value);
        } catch (SQLException | CandidateWithIdNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getRegistrationStatusById (int candidateId)  {
        try {
            return candidateStatusDAO.getRegistrationStatusById(candidateId);
        } catch (CandidateWithIdNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
