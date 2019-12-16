package models;

import entities.IssuedBooksFine;
import helpers.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IssuedBooksFineModel implements ModelInterface <IssuedBooksFine> {

    @Override
    public IssuedBooksFine getById(int id) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM ISSUED_BOOKS_FINES WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        return extractEntity(resultSet);
    }

    @Override
    public List<IssuedBooksFine> getAll() throws SQLException {
        List <IssuedBooksFine> fines = new ArrayList<>();
        Statement statement = Db.getInstance().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ISSUED_BOOKS_FINES");
        while (resultSet.next()){
            fines.add(extractEntity(resultSet));
        }
        return fines;
    }

    @Override
    public ResultSet getAllAsQueryResult() throws SQLException {
        return Db.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM ISSUED_BOOKS_FINES");
    }

    @Override
    public IssuedBooksFine extractEntity(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            IssuedBooksFine issuedBooksFine = new IssuedBooksFine();
            issuedBooksFine.setId(resultSet.getInt("ID"));
            issuedBooksFine.setIssuedBookId(resultSet.getInt("ISSUED_BOOK_ID"));
            issuedBooksFine.setFinedAt(resultSet.getTimestamp("FINED_AT"));
            issuedBooksFine.setReason(resultSet.getString("REASON"));
            return issuedBooksFine;
        }
        return null;
    }

    @Override
    public boolean insert(IssuedBooksFine model) throws SQLException {
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "INSERT INTO ISSUED_BOOKS_FINES (ISSUED_BOOK_ID, REASON) VALUES  (?, ?)"
            );
            preparedStatement.setInt(1, model.getIssuedBookId());
            preparedStatement.setString(2, model.getReason());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean update(IssuedBooksFine model) throws SQLException {
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "UPDATE ISSUED_BOOKS_FINES SET ISSUED_BOOK_ID = ?, REASON = ? WHERE  ID = ?"
            );
            preparedStatement.setInt(1, model.getIssuedBookId());
            preparedStatement.setString(2, model.getReason());
            preparedStatement.setInt(3, model.getId());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "DELETE FROM ISSUED_BOOKS_FINES WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);

        return preparedStatement.executeUpdate() > 0;
    }
}
