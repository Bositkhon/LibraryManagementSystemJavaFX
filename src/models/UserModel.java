package models;

import entities.Reservation;
import entities.User;
import helpers.Db;
import helpers.Md5Converter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UserModel implements ModelInterface<User> {

    @Override
    public User getById(int id) throws SQLException{
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM USERS WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return extractEntity(resultSet);
    }

    @Override
    public ResultSet getAllAsQueryResult() throws SQLException{
        Statement statement = Db.getInstance().getConnection().createStatement();
        return statement.executeQuery("SELECT * FROM USERS");
    }

    @Override
    public List<User> getAll() throws SQLException{
        List<User> users = new ArrayList<>();
        Statement statement = Db.getInstance().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");
        while(resultSet.next()){
            users.add(extractEntity(resultSet));
        }
        return users;
    }

    @Override
    public User extractEntity(ResultSet resultSet) throws SQLException{
        if(resultSet.next()){
            User user = new User();
            user.setId(resultSet.getInt("ID"));
            user.setUsername(resultSet.getString("USERNAME"));
            user.setPassword(resultSet.getString("PASSWORD"));
            user.setRoleId(resultSet.getInt("ROLE_ID"));
            user.setCreatedAt(resultSet.getTimestamp("CREATED_AT"));
            return user;
        }
        return null;
    }

    @Override
    public boolean insert(User model) throws SQLException{
        if(model.validate()){
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "INSERT INTO USERS (USERNAME, PASSWORD, ROLE_ID) VALUES  (?, ?, ?)"
            );
            preparedStatement.setString(1, model.getUsername());
            preparedStatement.setString(2, Md5Converter.hash(model.getPassword()));
            preparedStatement.setInt(3, model.getRoleId());
            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean update(User model) throws SQLException{
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "UPDATE USERS SET USERNAME = ?, PASSWORD = ?, ROLE_ID = ? WHERE ID = ?"
            );

            preparedStatement.setString(1, model.getUsername());
            preparedStatement.setString(2, model.getPassword());
            preparedStatement.setInt(3, model.getRoleId());
            preparedStatement.setInt(4, model.getId());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    public boolean update(User old_model, User model) throws SQLException{
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "UPDATE USERS SET USERNAME = ?, PASSWORD = ?, ROLE_ID = ? WHERE ID = ?"
            );

            preparedStatement.setString(1, model.getUsername());
            if(old_model.getPassword().equals(model.getPassword())){
                preparedStatement.setString(2, model.getPassword());
            }else{
                preparedStatement.setString(2, Md5Converter.hash(model.getPassword()));
            }
            preparedStatement.setInt(3, model.getRoleId());
            preparedStatement.setInt(4, model.getId());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException{
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "DELETE FROM USERS WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public List<User> getAllByCondition(Properties properties) throws SQLException{
        List<User> users = new ArrayList<>();
        String pairs = "";
        for(String key : properties.stringPropertyNames()){
            pairs += String.format("%s %s", key, properties.getProperty(key));
        }
        String query = String.format("SELECT * FROM USERS %s", pairs);
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            users.add(extractEntity(resultSet));
        }
        return users;
    }

    public List<User> getByRoleId(int id) throws SQLException{
        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM USERS WHERE ROLE_ID = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            users.add(extractEntity(resultSet));
        }
        return users;
    }

    public User getByUsernameAndPassword(String username) throws SQLException{
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM USERS WHERE USERNAME = ?"
        );
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        return extractEntity(resultSet);
    }
}