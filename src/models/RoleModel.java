package models;

import entities.Role;
import entities.User;
import helpers.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ROLES");
        Role role = extractEntity(resultSet);
        while(role != null){
            roles.add(role);
            role = extractEntity(resultSet);
        }
        return roles;
    }

    @Override
    public Role extractEntity(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            Role role = new Role();
            role.setId(resultSet.getInt("ID"));
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

    @Override
    public List<Role> getAllByCondition(Properties properties) throws SQLException {
        List<Role> roles = new ArrayList<>();
        String pairs = "";
        for(String key : properties.stringPropertyNames()){
            pairs += String.format("%s %s", key, properties.getProperty(key));
        }
        String query = String.format("SELECT * FROM ROLES %s", pairs);
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            roles.add(extractEntity(resultSet));
        }
        return roles;
    }

    public Role getByTitle(String title) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM ROLES WHERE TITLE = ?"
        );
        preparedStatement.setString(1, title);;
        ResultSet resultSet = preparedStatement.executeQuery();
        return extractEntity(resultSet);
    }

}
