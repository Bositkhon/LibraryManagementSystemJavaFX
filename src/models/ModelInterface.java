package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public interface ModelInterface<T> {

    T getById(int id) throws SQLException;

    List <T> getAll() throws SQLException;

    ResultSet getAllAsQueryResult() throws SQLException;

    List<T> getAllByCondition(Properties properties) throws SQLException;

    T extractEntity(ResultSet resultSet) throws SQLException;

    boolean insert(T model) throws SQLException;

    boolean update(T model) throws SQLException;

    boolean delete(int id) throws SQLException;

}
