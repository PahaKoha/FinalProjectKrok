package pkoh.krokFinalProject.services;

import pkoh.krokFinalProject.dataBase.SQLRequests.CreateTable;

import java.sql.Connection;

/**
 * Класс отвечает за реализацию методов из CreateTable
 */
public class CreateTableService {
    CreateTable createTable;

    public CreateTableService(Connection connection) {
        createTable = new CreateTable(connection);
    }

    public void createAllTables() {
        createTable.createCandidateTable();
        createTable.createDocumentTable();
        createTable.createCandidateStatusTable();
    }

    public void createCandidateTable() {
        createTable.createCandidateTable();
    }

    public void createDocumentTable() {
        createTable.createDocumentTable();
    }

    public void createCandidateStatusTable() {
        createTable.createCandidateStatusTable();
    }
}
