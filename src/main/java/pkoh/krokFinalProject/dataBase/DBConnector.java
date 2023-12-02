package pkoh.krokFinalProject.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Отвечает за соединение с базой данных
 */
public class DBConnector {
    private static final String JDBC_URL_PREFIX = "jdbc:h2:mem:./testDB";
    private static final String DB_NAME = "sa";
    private static final String DB_PASSWORD = "";

    /**
     * Метод для соединения с базой h2
     * @return - соединение с базой
     * @throws SQLException - срабатывает при возникновении проблем, связанных с SQL-запросам
     */
    public static Connection getDbConnection() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection(JDBC_URL_PREFIX,
                    DB_NAME, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
