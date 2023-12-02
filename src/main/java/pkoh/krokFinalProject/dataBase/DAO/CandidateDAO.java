package pkoh.krokFinalProject.dataBase.DAO;

import pkoh.krokFinalProject.customExceptions.CandidateAlreadyExistException;
import pkoh.krokFinalProject.customExceptions.CandidateWithIdNotFoundException;
import pkoh.krokFinalProject.customExceptions.CandidateWithSurnameNotFoundException;
import pkoh.krokFinalProject.dataBase.AbstractConnect;
import pkoh.krokFinalProject.entities.Candidate;
import pkoh.krokFinalProject.repository.CandidateStatusRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс, содержащий методы, для взаимодействия с таблицей candidate
 */
public class CandidateDAO extends AbstractConnect {
    private CandidateStatusRepository candidateStatusRepository;

    public CandidateDAO(Connection connection) {
        super(connection);
    }

    /**
     * Метод, который позволяет создать новый объект класса CandidateStatusRepository. Нужен, чтобы избежать рекурсию
     *
     * @return - объект класса CandidateStatusRepository
     */
    public CandidateStatusRepository getCandidateStatusRepository() {
        if (candidateStatusRepository == null) {
            candidateStatusRepository = new CandidateStatusRepository(this.connection);
        }
        return candidateStatusRepository;
    }

    /**
     * Метод добавляет нового кандидата в базу, а также ставит статус регистрации по умолчанию false
     *
     * @param candidate - кандидат, которого хотим положить в базу
     * @return - возвращает объект Candidate, которого положили в базу
     * @throws SQLException                          - срабатывает при возникновении проблем, связанных с SQL-запросами
     * @throws CandidateAlreadyExistException        - срабатывает, если кандидат с фамилией уже существует (предполагается,
     *                                               что фамилии у всех должны быть разные)
     * @throws CandidateWithSurnameNotFoundException - срабатывает, если кандидат с определенной фамилией не был найден
     */
    public Candidate addNewCandidate(Candidate candidate) throws SQLException, CandidateAlreadyExistException, CandidateWithSurnameNotFoundException {
        if (findCandidateBySurname(candidate.getSurname()) == null) {
            String SQLRequest = "INSERT INTO candidate (name, surname, age, political_experience, election_program) " +
                    "VALUES (?,?,?,?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQLRequest,
                    Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, candidate.getName());
                preparedStatement.setString(2, candidate.getSurname());
                preparedStatement.setInt(3, candidate.getAge());
                preparedStatement.setInt(4, candidate.getPoliticalExperience());
                preparedStatement.setString(5, candidate.getElectionProgram());
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    getCandidateStatusRepository().addNewCandidateRegistrationStatus(generatedId);
                    return new Candidate(generatedId, candidate.getName(), candidate.getSurname(),
                            candidate.getAge(), candidate.getPoliticalExperience(), candidate.getElectionProgram());
                } else {
                    return null;
                }

            }
        } else {
            throw new CandidateAlreadyExistException(candidate.getSurname());
        }
    }

    /**
     * Метод позволяет найти кандидата по id
     *
     * @param candidateId - id кандидата, которого хотим найти
     * @return - объект Candidate, если такого кандидата нет, то null
     * @throws SQLException - срабатывает при возникновении проблем, связанных с SQL-запросами
     */
    public Candidate findCandidateById(int candidateId) throws SQLException {
        String SQLRequest = "SELECT * FROM candidate WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLRequest,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, candidateId);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return new Candidate(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getInt("age"),
                        resultSet.getInt("political_experience"),
                        resultSet.getString("election_program"));
            } else {
                return null;
            }
        }
    }

    /**
     * Метод позволяет найти кандидата по фамилии
     *
     * @param candidateSurname фамилия кандидата, которого хотим найти
     * @return - объект Candidate. Если такого кандидата нет, то null
     * @throws SQLException - срабатывает при возникновении проблем, связанных с SQL-запросами
     */
    public Candidate findCandidateBySurname(String candidateSurname) throws SQLException {
        String SQLRequest = "SELECT * FROM candidate WHERE surname=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLRequest,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, candidateSurname);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return new Candidate(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getInt("age"),
                        resultSet.getInt("political_experience"),
                        resultSet.getString("election_program"));
            } else {
                return null;
            }
        }
    }

    /**
     * Метод, который позволяет удалить кандидата по его id
     *
     * @param candidateId - id кандидата, которого хотим удалить
     * @throws SQLException                     - срабатывает при возникновении проблем, связанных с SQL-запросами
     * @throws CandidateWithIdNotFoundException - срабатывает, когда кандидат с фамилией не был найден
     */
    public void deleteCandidateById(int candidateId) throws SQLException, CandidateWithIdNotFoundException {
        if (findCandidateById(candidateId) != null) {
            String SQLRequest = "DELETE FROM candidate WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQLRequest)) {
                preparedStatement.setInt(1, candidateId);
                preparedStatement.executeUpdate();
            }
        } else {
            throw new CandidateWithIdNotFoundException(candidateId);
        }
    }

    /**
     * Метод позволяет получить всех кандидатов их базы
     *
     * @return - set с кандидатами
     * @throws SQLException - срабатывает при возникновении проблем, связанных с SQL-запросами
     */
    public Set<Candidate> getAllCandidates() throws SQLException {
        Set<Candidate> candidates = new HashSet<>();
        try (Statement statement = connection.createStatement()) {
            String SQLRequest = "SELECT * FROM candidate";
            ResultSet resultSet = statement.executeQuery(SQLRequest);
            while (resultSet.next()) {
                candidates.add(
                        new Candidate(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("surname"),
                                resultSet.getInt("age"),
                                resultSet.getInt("political_experience"),
                                resultSet.getString("election_program")
                        )
                );
            }
            return candidates;
        }
    }
}
