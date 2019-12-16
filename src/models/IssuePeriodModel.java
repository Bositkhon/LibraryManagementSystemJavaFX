package models;

import entities.IssuePeriod;
import helpers.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IssuePeriodModel implements ModelInterface <IssuePeriod> {

    @Override
    public IssuePeriod getById(int id) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM ISSUE_PERIODS WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        return extractEntity(resultSet);
    }

    @Override
    public List<IssuePeriod> getAll() throws SQLException {
        List <IssuePeriod> issuePeriods = new ArrayList<>();
        Statement statement = Db.getInstance().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ISSUE_PERIODS");
        while(resultSet.next()){
            issuePeriods.add(extractEntity(resultSet));
        }
        return issuePeriods;
    }

    @Override
    public ResultSet getAllAsQueryResult() throws SQLException {
        return Db.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM ISSUE_PERIODS");
    }

    @Override
    public IssuePeriod extractEntity(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            IssuePeriod issuePeriod = new IssuePeriod();
            issuePeriod.setId(resultSet.getInt("ID"));
            issuePeriod.setTitle(resultSet.getString("TITLE"));
            issuePeriod.setDays(resultSet.getInt("DAYS"));
            return issuePeriod;
        }
        return null;
    }

    @Override
    public boolean insert(IssuePeriod model) throws SQLException {
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "INSERT INTO ISSUE_PERIODS (TITLE, DAYS) VALUES (?, ?)"
            );
            preparedStatement.setString(1, model.getTitle());
            preparedStatement.setInt(2, model.getDays());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean update(IssuePeriod model) throws SQLException {
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "UPDATE ISSUE_PERIODS SET TITLE = ?, DAYS = ?"
            );
            preparedStatement.setString(1, model.getTitle());
            preparedStatement.setInt(2, model.getDays());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "DELETE FROM ISSUE_PERIODS WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);

        return preparedStatement.executeUpdate() > 0;
    }

}
