package pkoh.krokFinalProject.dataBase.SQLRequests;

import pkoh.krokFinalProject.dataBase.AbstractConnect;
import pkoh.krokFinalProject.entities.Candidate;
import pkoh.krokFinalProject.entities.Document;
import pkoh.krokFinalProject.enums.DocumentType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс, который хранит методы, отвечающие за визуализацию таблиц (для удобства)
 */
public class TableInformationView extends AbstractConnect {
    public TableInformationView(Connection connection) {
        super(connection);
    }
    /**
     * Выводит в консоль строки таблицы candidate (для удобства)
     */
    public void showCandidateTable() {
        try (Statement statement = connection.createStatement()) {
            String SQLRequest = "SELECT * FROM candidate";
            ResultSet resultSet = statement.executeQuery(SQLRequest);
            while (resultSet.next()) {
                System.out.println(new Candidate(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getInt("age"),
                        resultSet.getInt("political_experience"),
                        resultSet.getString("election_program")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Выводит в консоль строки таблицы document (для удобства)
     */
    public void showDocumentTable() {
        try (Statement statement = connection.createStatement()) {
            String SQLRequest = "SELECT * FROM document";
            ResultSet resultSet = statement.executeQuery(SQLRequest);
            while (resultSet.next()) {
                System.out.println(new Document(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        DocumentType.valueOf(resultSet.getString("document_type")),
                        resultSet.getInt("candidate_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Выводит в консоль строки таблицы candidate_status (для удобства)
     */
    public void candidateRegistrationStatusView() {
        try (Statement statement = connection.createStatement()) {
            String SQLRequest = "SELECT * FROM candidate_status";
            ResultSet resultSet = statement.executeQuery(SQLRequest);
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt("id") +
                        ", candidate id: " + resultSet.getInt("candidate_id") +
                        ", is candidate registered: " +
                        (resultSet.getInt("registration_status") == 1)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showCombinedTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM candidate " +
                    "INNER JOIN candidate_status ON candidate.id = candidate_status.candidate_id");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                boolean registrationStatus = resultSet.getBoolean("registration_status");

                System.out.println("ID: " + id + ", Name: " + name + ", Surname: " + surname + ", Registration Status: " + registrationStatus);
            }
        }

    }

}
