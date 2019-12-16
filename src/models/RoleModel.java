package models;

import entities.Role;
import helpers.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoleModel implements ModelInterface<Role> {

    @Override
    public Role getById(int id) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM ROLES WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        return extractEntity(resultSet);
    }

    @Override
    public ResultSet getAllAsQueryResult() throws SQLException {
        Statement statement = Db.getInstance().getConnection().createStatement();
        return statement.executeQuery("SELECT  * FROM ROLES");
    }

    @Override
    public List<Role> getAll() throws SQLException {
        List <Role> roles = new ArrayList<>();
        Statement statement = Db.getInstance().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");
        while (resultSet.next()){
            roles.add(extractEntity(resultSet));
        }
        return roles;
    }

    @Override
    public Role extractEntity(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            Role role = new Role();
            role.setId(resultSet.getInt("id"));
            role.setTitle(resultSet.getString("TITLE"));
            role.setCreatedAt(resultSet.getTimestamp("CREATED_AT"));
            return role;
        }
        return null;
    }

    @Override
    public boolean insert(Role model) throws SQLException {
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "INSERT INTO ROLES (TITLE) VALUES (?)"
            );
            preparedStatement.setString(1, model.getTitle());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean update(Role model) throws SQLException {
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "UPDATE ROLES SET TITLE = ? WHERE ID = ?"
            );
            preparedStatement.setString(1, model.getTitle());
            preparedStatement.setInt(2, model.getId());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "DELETE FROM ROLES WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);

        return preparedStatement.executeUpdate() > 0;
    }

}
