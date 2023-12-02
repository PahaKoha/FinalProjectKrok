package pkoh.krokFinalProject.repository;

import pkoh.krokFinalProject.dataBase.DAO.DocumentDAO;
import pkoh.krokFinalProject.entities.Document;
import pkoh.krokFinalProject.enums.DocumentType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Промежуточный уровень между DAO-классами и реализацией DAO-классов
 */
public class DocumentRepository {
    private final DocumentDAO documentDAO;

    public DocumentRepository(Connection connection) {
        documentDAO = new DocumentDAO(connection);
    }

    public void addDocumentForCandidateById(int id, Document document) {
        try {
            documentDAO.addNewDocumentForCandidateById(id, document);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addDocumentForCandidateBySurname(String surname, Document document) {
        try {
            documentDAO.addNewDocumentForCandidateBySurname(surname, document);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Document> findDocumentsByCandidateId(int candidateId) {
        try {
            return documentDAO.findDocumentsByCandidateId(candidateId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDocumentByCandidateIdAndDocumentStatus (int candidateId, DocumentType documentType) {
        try {
            documentDAO.deleteDocumentByCandidateIdAndDocumentType(candidateId, documentType);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
