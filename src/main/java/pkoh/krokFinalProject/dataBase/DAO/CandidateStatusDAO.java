package pkoh.krokFinalProject.dataBase.DAO;

import pkoh.krokFinalProject.customExceptions.CandidateWithIdNotFoundException;
import pkoh.krokFinalProject.dataBase.AbstractConnect;
import pkoh.krokFinalProject.entities.Candidate;
import pkoh.krokFinalProject.repository.CandidateRepository;

import java.sql.*;

/**
 * Класс, который содержит методы для взаимодействия с таблицей candidate_status
 */
public class CandidateStatusDAO extends AbstractConnect {
    private CandidateRepository candidateRepository;

    public CandidateStatusDAO(Connection connection) {
        super(connection);
    }

    /**
     * Метод, который позволяет создать новый объект класса CandidateRepository. Нужен, чтобы избежать рекурсию
     *
     * @return - объект класса CandidateRepository
     */
    public CandidateRepository getCandidateStatusRepository() {
        if (candidateRepository == null) {
            candidateRepository = new CandidateRepository(this.connection);
        }
        return candidateRepository;
    }

    /**
     * Метод добавляет новую запись в таблицу candidate_status
     *
     * @param candidateId - id кандидата, для которого хотим добавить запись
     * @throws SQLException                     - срабатывает при возникновении проблем, связанных с SQL-запросам
     * @throws CandidateWithIdNotFoundException - срабатывает, когда кандидат с фамилией не был найден
     */
    public void addNewCandidateRegistrationStatus(int candidateId) throws SQLException
            , CandidateWithIdNotFoundException {
        Candidate candidate = getCandidateStatusRepository().findCandidateById(candidateId);
        if (candidate != null) {
            String SQLRequest = "INSERT INTO candidate_status (candidate_id, registration_status) VALUES (?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQLRequest)) {
                preparedStatement.setInt(1, candidateId);
                preparedStatement.setBoolean(2, false);
                preparedStatement.executeUpdate();
            }
        } else {
            throw new CandidateWithIdNotFoundException(candidateId);
        }
    }

    /**
     * Метод позволяет поменять статус регистрации кандидата
     *
     * @param candidateId - id кандидата, для которого хотим поменять статус
     * @param value       - значение, на которое хотим заменить
     * @throws SQLException                     - срабатывает при возникновении проблем, связанных с SQL-запросам
     * @throws CandidateWithIdNotFoundException - срабатывает, когда кандидат с фамилией не был найден
     */
    public void setCandidateRegistrationStatus(int candidateId, boolean value) throws SQLException
            , CandidateWithIdNotFoundException {
        Candidate candidate = getCandidateStatusRepository().findCandidateById(candidateId);
        if (candidate != null) {
            String SQLRequest = "UPDATE candidate_status SET registration_status = ? WHERE candidate_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(SQLRequest)) {
                statement.setInt(2, candidateId);
                statement.setBoolean(1, value);
                statement.executeUpdate();
            }
        } else {
            throw new CandidateWithIdNotFoundException(candidateId);
        }
    }

    /**
     * Метод позволяет получить статус регистрации определенного кандидата
     *
     * @param candidateId - id кандидата, для которого хотим получить статус
     * @return - true или false в зависимости от значения в таблице
     * @throws SQLException                     - срабатывает при возникновении проблем, связанных с SQL-запросам
     * @throws CandidateWithIdNotFoundException - срабатывает, когда кандидат с фамилией не был найден
     */
    public boolean getRegistrationStatusById(int candidateId) throws SQLException, CandidateWithIdNotFoundException {
        String SQLRequest = "SELECT registration_status FROM candidate_status WHERE candidate_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLRequest)) {
            preparedStatement.setInt(1, candidateId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("registration_status") == 1;
            } else {
                throw new CandidateWithIdNotFoundException(candidateId);
            }
        }
    }
}
