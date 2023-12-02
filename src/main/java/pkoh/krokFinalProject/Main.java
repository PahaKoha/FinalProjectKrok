package pkoh.krokFinalProject;

import pkoh.krokFinalProject.customExceptions.RegistrationException;
import pkoh.krokFinalProject.dataBase.DBConnector;
import pkoh.krokFinalProject.dataBase.SQLRequests.TableInformationView;
import pkoh.krokFinalProject.entities.Candidate;
import pkoh.krokFinalProject.entities.Document;
import pkoh.krokFinalProject.enums.DocumentType;
import pkoh.krokFinalProject.services.DocumentService;
import pkoh.krokFinalProject.services.CreateTableService;
import pkoh.krokFinalProject.services.CandidateService;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Использую этот класс только для демонстрации работы методов. Методы, которые выводят что-то в консоль нужны только для
 * более приятного визуального представления.
 */

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DBConnector.getDbConnection()) {
            CreateTableService createTableService = new CreateTableService(connection);
            createTableService.createAllTables();

            CandidateService candidateService = new CandidateService(connection);
            DocumentService documentService = new DocumentService(connection);
            TableInformationView tableInformationView = new TableInformationView(connection);

            // Добавляем кандидатов в базу из файла
            candidateService.addAllCandidatesFromFile("src/main/java/pkoh/krokFinalProject/CSVFiles/candidates.txt");

            //Добавляем каждому кандидату необходимый перечень документов
            for (Candidate candidate : candidateService.getAllCandidates()) {
                documentService.addNewDocumentForCandidateById(candidate.getId(), new Document("pasport", DocumentType.PASPORT));
                documentService.addNewDocumentForCandidateById(candidate.getId(), new Document("registration", DocumentType.REGISTRATION_CERTIFICATE));
            }

            //Выводим всех кандидатов и их статусы в консоль до регистрации
            System.out.println("Таблица до регистрации");
            tableInformationView.showCombinedTables();

            //Пытаемся регистрировать каждого кандидата
            for (Candidate candidate : candidateService.getAllCandidates()) {
                candidateService.registration(candidate.getId());
            }

            //Выводим всех кандидатов и их статусы в консоль после регистрации
            System.out.println("Таблица после регистрации:");
            tableInformationView.showCombinedTables();

            documentService.deleteDocumentByCandidateIdAndDocumentStatus(2, DocumentType.PASPORT);
            documentService.deleteDocumentByCandidateIdAndDocumentStatus(4, DocumentType.PASPORT);
            documentService.deleteDocumentByCandidateIdAndDocumentStatus(7, DocumentType.PASPORT);
            documentService.deleteDocumentByCandidateIdAndDocumentStatus(13, DocumentType.PASPORT);
            documentService.deleteDocumentByCandidateIdAndDocumentStatus(14, DocumentType.PASPORT);
            documentService.deleteDocumentByCandidateIdAndDocumentStatus(19, DocumentType.PASPORT);

            //Выводим всех кандидатов и их статусы в консоль после удаления некоторых документов
            System.out.println("Таблица после удаления документов:");
            tableInformationView.showCombinedTables();

            candidateService.deleteCandidateById(20);
            candidateService.deleteCandidateById(7);

            //Выводим всех кандидатов и их статусы в консоль после удаления кандидатов
            System.out.println("Таблица после удаления кандидатов:");
            tableInformationView.showCombinedTables();


        } catch (SQLException | RegistrationException e) {
            throw new RuntimeException(e);
        }
    }
}
