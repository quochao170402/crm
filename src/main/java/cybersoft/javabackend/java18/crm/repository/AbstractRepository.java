package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.exception.DatabaseNotFoundException;
import cybersoft.javabackend.java18.crm.jdbc.MySQLConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AbstractRepository<T> {

    public List<T> executeQuery(JdbcExecute<List<T>> processor) {
        try (Connection connection = MySQLConnection.getConnection()) {
            return processor.processQuery(connection);
        } catch (SQLException e) {
            throw new DatabaseNotFoundException(e.getMessage());
        }
    }

    public T executeQuerySingle(JdbcExecute<T> processor) {
        try (Connection connection = MySQLConnection.getConnection()) {
            return processor.processQuery(connection);
        } catch (SQLException e) {
            throw new DatabaseNotFoundException(e.getMessage());
        }
    }

    public int executeUpdate(JdbcExecute<Integer> processor) {
        try (Connection connection = MySQLConnection.getConnection()) {
            return processor.processQuery(connection);
        } catch (SQLException e) {
            throw new DatabaseNotFoundException(e.getMessage());
        }
    }

}
