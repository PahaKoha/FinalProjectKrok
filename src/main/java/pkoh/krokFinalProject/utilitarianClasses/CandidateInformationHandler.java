package pkoh.krokFinalProject.utilitarianClasses;

import pkoh.krokFinalProject.entities.Candidate;
import pkoh.krokFinalProject.entities.Document;
import pkoh.krokFinalProject.info.BlackList;
import pkoh.krokFinalProject.repository.CandidateStatusRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CandidateInformationHandler {
    private CandidateInformationHandler() {

    }
    /**
     * Метод проверяет, есть ли запрещенные слова в предвыборной программе кандидата
     * @param candidate - объект Candidate
     * @return - true если есть хотя бы одно запрещенное слово, false, если нет совпадений
     */
    public static boolean areThereAnyRudeWordsInTheElectionProgram(Candidate candidate) {
        return UsefulTools.getElementsFromString(candidate.getElectionProgram()).
                stream().anyMatch(str -> BlackList.blackWordsList.contains(str));
    }

    /**
     * Метод проверяет достаточный ли у кандидата политический опыт и достиг ли он возраста, при котором можно участвовать
     * в выборах
     * @param candidate - объект Candidate
     * @return - true елис может участвовать, false если нет
     */
    public static boolean sufficientPoliticalExperience(Candidate candidate) {
        return candidate.getAge() >= 20 && candidate.getPoliticalExperience() >= 10;
    }

    /**
     * Берет информацию из строк, которые были получены из файла и создает на их основе set из Candidate
     * @param path - путь к файлу
     * @return - set с кандидатами
     */
    public static Set<Candidate> getCandidateFromCSVLine(String path) {
        Set<String> set = CSVHandler.getCandidatesInfoFromCSV(path);
        return set.stream().map(line -> {
            List<String> candidateInfo = UsefulTools.getElementsFromCSVLine(line);
            return new Candidate(
                    candidateInfo.get(0),
                    candidateInfo.get(1),
                    Integer.parseInt(candidateInfo.get(2)),
                    Integer.parseInt(candidateInfo.get(3)),
                    candidateInfo.get(4)
            );
        }).collect(Collectors.toSet());
    }

    /**
     * Этот метод проверяет все условия, при которых кандидат может быть зарегистрирован
     * @param candidateDocuments - набор документов кандидата
     * @param candidate - объект кандидата
     * @param candidateId - id кандидата
     * @param candidateStatusRepository - объект CandidateStatusRepository для проверки на то, не зарегистрирован ли кандидат
     * @return true если кандидат может быть зарегистрирован, false, если нет
     */
    public static boolean checkAllConditionsForRegistration (Set<Document> candidateDocuments
            , Candidate candidate, int candidateId, CandidateStatusRepository candidateStatusRepository) {
       return DocumentHandler.isAllDocumentsCandidateHas(candidateDocuments) &&
                !areThereAnyRudeWordsInTheElectionProgram(candidate) &&
                sufficientPoliticalExperience(candidate) &&
                !candidateStatusRepository.getRegistrationStatusById(candidateId);
    }
}
