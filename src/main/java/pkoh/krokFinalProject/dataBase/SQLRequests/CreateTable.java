package pkoh.krokFinalProject.dataBase.SQLRequests;

import pkoh.krokFinalProject.dataBase.AbstractConnect;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс, который хранит методы, для создания таблиц
 */
public class CreateTable extends AbstractConnect {
    public CreateTable(Connection connection) {
        super(connection);
    }
    /**
     * Метод, который создает таблицу candidate
     */
    public void createCandidateTable() {
        try (Statement statement = connection.createStatement()) {
            String SQLRequest = "CREATE TABLE candidate (" +
                    "id  IDENTITY NOT NULL, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "surname VARCHAR(100) NOT NULL UNIQUE, " +
                    "age INT NOT NULL, " +
                    "political_experience INT NOT NULL, " +
                    "election_program VARCHAR(50000)," +
                    "PRIMARY KEY (id)" +
                    ")";
            statement.executeUpdate(SQLRequest);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод, который создает таблицу document
     */
    public void createDocumentTable() {
        try (Statement statement = connection.createStatement()) {
            String SQLRequest = "CREATE TABLE document (" +
                    "id IDENTITY NOT NULL, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "document_type VARCHAR(100) NOT NULL, " +
                    "candidate_id INT NOT NULL ," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (candidate_id) REFERENCES candidate(id) ON DELETE CASCADE" +
                    ")";
            statement.executeUpdate(SQLRequest);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод, который создает таблицу candidate_status
     */
    public void createCandidateStatusTable() {
        try (Statement statement = connection.createStatement()) {
            String SQLRequest = "CREATE TABLE candidate_status (" +
                    "id IDENTITY NOT NULL, " +
                    "candidate_id INT NOT NULL, " +
                    "registration_status BOOLEAN DEFAULT FALSE NOT NULL , " +
                    "PRIMARY KEY (id), " +
                    "FOREIGN KEY (candidate_id) REFERENCES candidate(id) ON DELETE CASCADE" +
                    ")";
            statement.executeUpdate(SQLRequest);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
