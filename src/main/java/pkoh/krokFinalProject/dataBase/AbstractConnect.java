package pkoh.krokFinalProject.dataBase;

import java.sql.Connection;

/**
 * Класс родитель для классов, которые используют методы для подключения к базе данных
 */
public abstract class AbstractConnect {

    protected final Connection connection;
    public AbstractConnect(Connection connection) {
        this.connection = connection;
    }
}
