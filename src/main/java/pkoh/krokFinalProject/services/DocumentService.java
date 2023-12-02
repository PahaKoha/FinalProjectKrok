package pkoh.krokFinalProject.services;

import pkoh.krokFinalProject.entities.Document;
import pkoh.krokFinalProject.enums.DocumentType;
import pkoh.krokFinalProject.repository.CandidateStatusRepository;
import pkoh.krokFinalProject.repository.DocumentRepository;

import java.sql.Connection;

public class DocumentService {
    private final DocumentRepository documentRepository;
    private final CandidateStatusRepository candidateStatusRepository;

    public DocumentService(Connection connection) {
        documentRepository = new DocumentRepository(connection);
        candidateStatusRepository = new CandidateStatusRepository(connection);
    }

    public void addNewDocumentForCandidateById(int candidateId, Document document) {
        documentRepository.addDocumentForCandidateById(candidateId, document);
    }

    public void addNewDocumentForCandidateBySurname(String candidateSurname, Document document) {
        documentRepository.addDocumentForCandidateBySurname(candidateSurname, document);
    }

    /**
     * Метод, который удаляет документ у определенного кандидата, и если кандидат зарегистрирован, то меняет его статус
     * на false, так как документов уже не хватает
     * @param candidateId - id кандидата, у которого хотим удалить документ
     * @param documentType - тип документа
     */
    public void deleteDocumentByCandidateIdAndDocumentStatus (int candidateId, DocumentType documentType)  {
        if (candidateStatusRepository.getRegistrationStatusById(candidateId)) {
            documentRepository.deleteDocumentByCandidateIdAndDocumentStatus(candidateId, documentType);
            candidateStatusRepository.setCandidateRegistrationStatus(candidateId, false);
        } else {
            documentRepository.deleteDocumentByCandidateIdAndDocumentStatus(candidateId, documentType);
        }
    }
}
