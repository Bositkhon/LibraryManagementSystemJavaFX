package models;

import entities.IssuedBook;
import entities.User;
import helpers.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class IssuedBookModel implements ModelInterface <IssuedBook> {

    @Override
    public IssuedBook getById(int id) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM ISSUED_BOOKS WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        return extractEntity(resultSet);
    }

    @Override
    public List<IssuedBook> getAll() throws SQLException {
        List <IssuedBook> issuedBooks = new ArrayList<>();
        Statement statement = Db.getInstance().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ISSUED_BOOKS");
        while (resultSet.next()){
            issuedBooks.add(extractEntity(resultSet));
        }
        return issuedBooks;
    }

    @Override
    public ResultSet getAllAsQueryResult() throws SQLException {
        return Db.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM ISSUED_BOOKS");
    }

    @Override
    public IssuedBook extractEntity(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            IssuedBook issuedBook = new IssuedBook();
            issuedBook.setId(resultSet.getInt("ID"));
            issuedBook.setStatus(
                    IssuedBook.Status.getStatusByTitle(resultSet.getString("STATUS"))
            );
            issuedBook.setUserId(resultSet.getInt("USER_ID"));
            issuedBook.setBookId(resultSet.getInt("BOOK_ID"));
            issuedBook.setPeriodId(resultSet.getInt("PERIOD_ID"));
            issuedBook.setDue(resultSet.getDate("DUE"));
            issuedBook.setIssuedAt(resultSet.getTimestamp("ISSUED_AT"));
            return issuedBook;
        }
        return null;
    }

    @Override
    public boolean insert(IssuedBook model) throws SQLException {
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "INSERT INTO ISSUED_BOOKS (STATUS, USER_ID, BOOK_ID, PERIOD_ID, DUE) VALUES (?, ?, ?, ?, ?)"
            );
            preparedStatement.setInt(1, IssuedBook.Status.getStatusId(model.getStatus()));
            preparedStatement.setInt(2, model.getUserId());
            preparedStatement.setInt(3, model.getBookId());
            preparedStatement.setInt(4, model.getPeriodId());
            preparedStatement.setDate(5, model.getDue());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean update(IssuedBook model) throws SQLException {
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "UPDATE ISSUED_BOOKS SET STATUS = ? , USER_ID = ?, BOOK_ID = ? , PERIOD_ID = ? , DUE = ? WHERE ID = ?"
            );
            preparedStatement.setInt(1, IssuedBook.Status.getStatusId(model.getStatus()));
            preparedStatement.setInt(2, model.getUserId());
            preparedStatement.setInt(3, model.getBookId());
            preparedStatement.setInt(4, model.getPeriodId());
            preparedStatement.setDate(5, model.getDue());
            preparedStatement.setInt(6, model.getId());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "DELETE FROM ISSUED_BOOKS WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public List<IssuedBook> getAllByCondition(Properties properties) throws SQLException {
        List<IssuedBook> issuedBooks = new ArrayList<>();
        String pairs = "";
        for(String key : properties.stringPropertyNames()){
            pairs += String.format("%s %s", key, properties.getProperty(key));
        }
        String query = String.format("SELECT * FROM ISSUED_BOOKS %s", pairs);
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            issuedBooks.add(extractEntity(resultSet));
        }
        return issuedBooks;
    }

    public List<IssuedBook> getByUserId(int id) throws SQLException{
        List<IssuedBook> issuedBooks = new ArrayList<>();
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM ISSUED_BOOKS WHERE USER_ID = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            issuedBooks.add(extractEntity(resultSet));
        }
        return issuedBooks;
    }

    public List<IssuedBook> getByBookId(int id) throws SQLException{
        List<IssuedBook> issuedBooks = new ArrayList<>();
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM ISSUED_BOOKS WHERE BOOK_ID = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            issuedBooks.add(extractEntity(resultSet));
        }
        return issuedBooks;
    }

    public List<IssuedBook> getByPeriodId(int id) throws SQLException{
        List<IssuedBook> issuedBooks = new ArrayList<>();
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM ISSUED_BOOKS WHERE PERIOD_ID = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            issuedBooks.add(extractEntity(resultSet));
        }
        return issuedBooks;
    }
}