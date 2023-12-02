package pkoh.krokFinalProject.services;

import pkoh.krokFinalProject.customExceptions.RegistrationException;
import pkoh.krokFinalProject.entities.Candidate;
import pkoh.krokFinalProject.entities.Document;
import pkoh.krokFinalProject.repository.CandidateRepository;
import pkoh.krokFinalProject.repository.CandidateStatusRepository;
import pkoh.krokFinalProject.repository.DocumentRepository;
import pkoh.krokFinalProject.utilitarianClasses.CandidateInformationHandler;

import java.sql.Connection;
import java.util.Set;

/**
 * Класс отвечает за реализацию методов из CandidateRepository
 */
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final DocumentRepository documentRepository;
    private final CandidateStatusRepository candidateStatusRepository;

    public CandidateService(Connection connection) {
        candidateRepository = new CandidateRepository(connection);
        documentRepository = new DocumentRepository(connection);
        candidateStatusRepository = new CandidateStatusRepository(connection);
    }

    /**
     * Метод, который "регистрирует кандидата". Меняет статус регистрации
     * @param candidateId - id кандидата, которого хотим зарегистрировать
     * @throws RegistrationException - срабатывает, когда пытаются зарегистрировать кандидата с не существующим id
     */
    public void registration(int candidateId) throws RegistrationException {
        Candidate candidate = candidateRepository.findCandidateById(candidateId);
        if (candidate != null) {
            Set<Document> candidateDocuments = documentRepository.findDocumentsByCandidateId(candidate.getId());
            if (CandidateInformationHandler.checkAllConditionsForRegistration(candidateDocuments, candidate
                    , candidateId, candidateStatusRepository)) {
                candidateStatusRepository.setCandidateRegistrationStatus(candidateId, true);
            }
        } else {
            throw new RegistrationException(candidateId);
        }

    }

    public void addNewCandidate(Candidate candidate) {
        candidateRepository.addCandidateToDb(candidate);
    }

    /**
     * Этот метод предназначен специально для того, чтобы занести кандидатов из файла в базу
     * @param path - путь к файлу
     */
    public void addAllCandidatesFromFile(String path) {
        CandidateInformationHandler.getCandidateFromCSVLine(path).
                forEach(this::addNewCandidate);
    }

    public void deleteCandidateById(int candidateId) {
        candidateRepository.deleteCandidateById(candidateId);
    }

    public Set<Candidate> getAllCandidates () {
            return candidateRepository.getAllCandidates();
    }
}
