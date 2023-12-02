package pkoh.krokFinalProject.customExceptions;

/**
 * Исключение, выбрасываемое, когда не удается найти кандидата с указанным id.
 */
public class CandidateWithIdNotFoundException extends Exception {

    private final int candidateId;

    public CandidateWithIdNotFoundException(int candidateId) {
        this.candidateId = candidateId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    @Override
    public String getMessage() {
        return "Кандидата с id: " + candidateId + " не существует.";
    }
}