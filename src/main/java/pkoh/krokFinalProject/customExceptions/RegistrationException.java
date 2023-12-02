package pkoh.krokFinalProject.customExceptions;

/**
 * Исключение, которое выбрасывается при возникновении ошибок в процессе регистрации кандидата.
 */
public class RegistrationException extends Exception {

    private final int candidateId;

    public RegistrationException(int candidateId) {
        this.candidateId = candidateId;
    }

    @Override
    public String getMessage() {
        return "Ошибка регистрации: кандидата с id: " + candidateId + " не существует";
    }
}
