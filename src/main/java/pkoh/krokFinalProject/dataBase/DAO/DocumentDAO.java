package pkoh.krokFinalProject.dataBase.DAO;

import pkoh.krokFinalProject.dataBase.AbstractConnect;
import pkoh.krokFinalProject.entities.Candidate;
import pkoh.krokFinalProject.entities.Document;
import pkoh.krokFinalProject.enums.DocumentType;
import pkoh.krokFinalProject.repository.CandidateRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс, который содержит методы для взаимодействия с таблицей document
 */
public class DocumentDAO extends AbstractConnect {
    private final CandidateRepository candidateRepository;

    public DocumentDAO(Connection connection) {
        super(connection);
        candidateRepository = new CandidateRepository(this.connection);
    }


    /**
     * Метод, который добавляет документ определенному кандидату по его id
     *
     * @param candidateId - id кандидата, для которого нудно добавить документ
     * @param document    - документ, который хотим добавить
     * @throws SQLException - - срабатывает при возникновении проблем, связанных с SQL-запросам
     */
    public void addNewDocumentForCandidateById(int candidateId, Document document) throws SQLException {
        String SQLRequest = "INSERT INTO document (name, document_type, candidate_id) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLRequest)) {
            Candidate candidate = candidateRepository.findCandidateById(candidateId);
            preparedStatement.setString(1, document.getName());
            preparedStatement.setString(2, document.getDocumentType().toString());
            preparedStatement.setInt(3, candidate.getId());
            preparedStatement.executeUpdate();
        }
    }
    /**
     * Метод, который добавляет документ определенному кандидату по его фамилии
     *
     * @param candidateSurname - фамилия кандидата, для которого нудно добавить документ
     * @param document    - документ, который хотим добавить
     * @throws SQLException - срабатывает при возникновении проблем, связанных с SQL-запросам
     */
    public void addNewDocumentForCandidateBySurname(String candidateSurname, Document document) throws SQLException {
        String SQLRequest = "INSERT INTO document (name, document_type, candidate_id) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLRequest)) {
            Candidate candidate = candidateRepository.findCandidateBySurname(candidateSurname);
            preparedStatement.setString(1, document.getName());
            preparedStatement.setString(2, document.getDocumentType().toString());
            preparedStatement.setInt(3, candidate.getId());
            preparedStatement.executeUpdate();
        }
    }


    /**
     * Метод, который позволяет получить список документов определенного кандидата
     *
     * @param candidateId - id кандидата, для которого хотим получить документы
     * @return - список документов для определенного кандидата
     * @throws SQLException - срабатывает при возникновении проблем, связанных с SQL-запросам
     */
    public Set<Document> findDocumentsByCandidateId(int candidateId) throws SQLException {
        Set<Document> candidateDocuments = new HashSet<>();
        try (Statement statement = connection.createStatement()) {
            String SQLRequest = "SELECT * FROM document WHERE candidate_id=" + candidateId;
            ResultSet resultSet = statement.executeQuery(SQLRequest);
            while (resultSet.next()) {
                candidateDocuments.add(new Document(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        DocumentType.valueOf(resultSet.getString("document_type")),
                        resultSet.getInt("candidate_id")
                ));
            }
            return candidateDocuments;
        }
    }

    /**
     * Метод позволяет удалить документ определенного типа по id кандидата
     * @param candidateId - id кандидата, у которого хотим удалить документ
     * @param documentType - тип документа, который хотим удалить
     * @throws SQLException - срабатывает при возникновении проблем, связанных с SQL-запросам
     */
    public void deleteDocumentByCandidateIdAndDocumentType(int candidateId, DocumentType documentType) throws SQLException {
        String SQLRequest = "DELETE FROM document WHERE candidate_id=? AND document_type=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLRequest)) {
            preparedStatement.setInt(1, candidateId);
            preparedStatement.setString(2, documentType.toString());
            preparedStatement.executeUpdate();
        }
    }
}
