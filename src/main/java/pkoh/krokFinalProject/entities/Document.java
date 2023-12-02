package pkoh.krokFinalProject.entities;

import pkoh.krokFinalProject.enums.DocumentType;

/**
 * Сущность документа
 */
public class Document {
    private int id;
    private final String name;
    private final DocumentType documentType;
    private int candidateId;

    public Document(String name, DocumentType documentType, int candidateId) {
        this.name = name;
        this.documentType = documentType;
        this.candidateId = candidateId;
    }

    public Document(int id, String name, DocumentType documentType, int candidateId) {
        this.id = id;
        this.name = name;
        this.documentType = documentType;
        this.candidateId = candidateId;
    }

    public Document(String name, DocumentType documentType) {
        this.name = name;
        this.documentType = documentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", documentType=" + documentType +
                ", candidateId=" + candidateId +
                '}';
    }
}
