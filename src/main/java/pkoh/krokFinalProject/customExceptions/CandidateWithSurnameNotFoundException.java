package pkoh.krokFinalProject.customExceptions;

/**
 * Исключение, выбрасываемое, если кандидат с указанной фамилией не найден.
 */
public class CandidateWithSurnameNotFoundException extends Exception {


    private final String candidateSurname;

    public CandidateWithSurnameNotFoundException(String candidateSurname) {
        this.candidateSurname = candidateSurname;
    }

    @Override
    public String getMessage() {
        return "Кандидата с фамилией: " + candidateSurname + " не существует.";
    }
}
