package pkoh.krokFinalProject.utilitarianClasses;


import pkoh.krokFinalProject.entities.Document;
import pkoh.krokFinalProject.enums.DocumentType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DocumentHandler {
    private DocumentHandler() {

    }

    /**
     * Метод проверяет, есть ли у кандидата все подходящие документы для регистрации
     * @param candidateDocuments - список документов, которые есть у кандидата
     * @return - true если есть все необходимые документы, false, если нет
     */
    public static boolean isAllDocumentsCandidateHas(Set<Document> candidateDocuments) {
        return candidateDocuments.stream().map(Document::getDocumentType).
                collect(Collectors.toSet()).
                containsAll(new HashSet<>(Set.of(DocumentType.PASPORT, DocumentType.REGISTRATION_CERTIFICATE)));
    }
}
