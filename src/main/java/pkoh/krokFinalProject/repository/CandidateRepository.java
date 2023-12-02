package pkoh.krokFinalProject.repository;

import pkoh.krokFinalProject.customExceptions.CandidateAlreadyExistException;
import pkoh.krokFinalProject.customExceptions.CandidateWithIdNotFoundException;
import pkoh.krokFinalProject.customExceptions.CandidateWithSurnameNotFoundException;
import pkoh.krokFinalProject.dataBase.DAO.CandidateDAO;
import pkoh.krokFinalProject.entities.Candidate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Промежуточный уровень между DAO-классами и реализацией DAO-классов
 */
public class CandidateRepository {
    private final CandidateDAO candidateDAO;

    public CandidateRepository(Connection connection) {
        candidateDAO = new CandidateDAO(connection);
    }

    public Candidate addCandidateToDb(Candidate candidate)  {
        try {
            return candidateDAO.addNewCandidate(candidate);
        } catch (SQLException | CandidateAlreadyExistException | CandidateWithSurnameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Candidate findCandidateById(int id)  {
        try {
            return candidateDAO.findCandidateById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Candidate findCandidateBySurname(String surname)  {
        try {
            return candidateDAO.findCandidateBySurname(surname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCandidateById(int candidateId) {
        try {
            candidateDAO.deleteCandidateById(candidateId);
        } catch (SQLException | CandidateWithIdNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Candidate> getAllCandidates () {
        try {
            return candidateDAO.getAllCandidates();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
