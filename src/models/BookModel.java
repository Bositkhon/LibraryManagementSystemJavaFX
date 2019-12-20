package models;

import entities.Book;
import entities.User;
import helpers.Db;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BookModel implements ModelInterface <Book> {

    @Override
    public Book getById(int id) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM BOOKS WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return extractEntity(resultSet);
    }

    @Override
    public List<Book> getAll() throws SQLException {
        List <Book> books = new ArrayList<>();
        Statement statement = Db.getInstance().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM BOOKS");
        Book book = extractEntity(resultSet);
        while (book != null){
            books.add(book);
            book = extractEntity(resultSet);
        }
        return books;
    }

    @Override
    public ResultSet getAllAsQueryResult() throws SQLException {
        return Db.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM BOOKS");
    }

    @Override
    public Book extractEntity(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            Book book = new Book();
            book.setId(resultSet.getInt("ID"));
            book.setQuantity(resultSet.getInt("QUANTITY"));
            book.setTitle(resultSet.getString("TITLE"));
            book.setSubject(resultSet.getString("SUBJECT"));
            book.setAuthor(resultSet.getString("AUTHOR"));
            book.setIsbn(resultSet.getString("ISBN"));
            book.setPublishedDate(resultSet.getDate("PUBLISHED_DATE"));
            book.setCreatedAt(resultSet.getTimestamp("CREATED_AT"));
            return book;
        }
        return null;
    }

    @Override
    public boolean insert(Book model) throws SQLException {
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "INSERT INTO BOOKS (QUANTITY, TITLE, SUBJECT, AUTHOR, ISBN, PUBLISHED_DATE) VALUES (?, ?, ?, ?, ?, ?)"
            );
            preparedStatement.setInt(1, model.getQuantity());
            preparedStatement.setString(2, model.getTitle());
            preparedStatement.setString(3, model.getSubject());
            preparedStatement.setString(4, model.getAuthor());
            preparedStatement.setString(5, model.getIsbn());
            preparedStatement.setDate(6, model.getPublishedDate());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean update(Book model) throws SQLException {
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "UPDATE BOOKS SET QUANTITY = ?, TITLE = ?, SUBJECT = ?, AUTHOR = ?, ISBN = ? , PUBLISHED_DATE = ? WHERE ID = ?"
            );
            preparedStatement.setInt(1, model.getQuantity());
            preparedStatement.setString(2, model.getTitle());
            preparedStatement.setString(3, model.getSubject());
            preparedStatement.setString(4, model.getAuthor());
            preparedStatement.setString(5, model.getIsbn());
            preparedStatement.setDate(6, model.getPublishedDate());
            preparedStatement.setInt(7, model.getId());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "DELETE FROM BOOKS WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);;
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public List<Book> getAllByCondition(Properties properties) throws SQLException {
        List<Book> books = new ArrayList<>();
        String pairs = "";
        for(String key : properties.stringPropertyNames()){
            pairs += String.format("%s %s", key, properties.getProperty(key));
        }
        String query = String.format("SELECT * FROM BOOKS %s", pairs);
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            books.add(extractEntity(resultSet));
        }
        return books;
    }

    public Book getByIsbn(String isbn) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM BOOKS WHERE ISBN = ?"
        );
        preparedStatement.setString(1, isbn);
        ResultSet resultSet = preparedStatement.executeQuery();
        return extractEntity(resultSet);
    }

    public List<Book> search(String value) throws SQLException {
        value = "%" + value;
        value = value + "%";
        List<Book> books = new ArrayList<>();
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM BOOKS WHERE TITLE LIKE ? OR SUBJECT LIKE ? OR AUTHOR LIKE ? OR ISBN LIKE ?"
        );
        preparedStatement.setString(1, value);
        preparedStatement.setString(2, value);
        preparedStatement.setString(3, value);
        preparedStatement.setString(4, value);

        ResultSet resultSet = preparedStatement.executeQuery();
        Book book = extractEntity(resultSet);
        while (book != null){
            books.add(book);
            book = extractEntity(resultSet);
        }
        return books;
    }

}
