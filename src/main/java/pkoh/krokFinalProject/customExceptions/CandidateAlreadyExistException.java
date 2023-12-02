package pkoh.krokFinalProject.customExceptions;

/**
 * Исключение, выбрасываемое, когда попытка добавить кандидата, чья фамилия уже существует,
 * приводит к конфликту.
 */
public class CandidateAlreadyExistException extends Exception {
    private final String candidateSurname;

    public CandidateAlreadyExistException(String candidateSurname) {
        this.candidateSurname = candidateSurname;
    }


    @Override
    public String getMessage() {
        return "Кандидат с фамилией " + candidateSurname + " уже существует";
    }
}
