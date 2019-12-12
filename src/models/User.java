package models;

import helpers.Db;
import helpers.Md5Converter;
import interfaces.DataAccessInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User extends BaseModel implements DataAccessInterface<User> {

    private int id;

    private String username;

    private String password;

    private int role_id;

    @Override
    public String getTableName() {
        return "USERS";
    }

    public User(){
        super();
    }

    User(String username, String password, int role_id){
        super();
        this.setUsername(username);
        this.setPassword(password);
        this.setRoleID(role_id);
    }

    protected User(int id, String username, String password, int role_id){
        this.setId(id);
        this.setUsername(username);
        this.setPassword(password);
        this.setRoleID(role_id);
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {

        Pattern pattern = Pattern.compile("^\\w+$");
        Matcher matcher = pattern.matcher(username);

        if(username.isEmpty()){
            this.addError("Username can not be empty");
        }else if(!matcher.find()){
            this.addError("Username should consist of letter and digits only");
        }else{
            this.username = username;
        }

    }

    private String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        Pattern pattern = Pattern.compile("^\\w+$");
        Matcher matcher = pattern.matcher(password);

        if(username.isEmpty()){
            this.addError("Password can not be empty");
        }else if(!matcher.find()){
            this.addError("Password should consist of letters and digits only");
        }else{
            this.password = Md5Converter.hash(password);
        }
    }

    public int getRoleID() {
        return role_id;
    }

    public void setRoleID(int role_id) {
        this.role_id = role_id;
    }

    public Properties getAttributeTypes(){
        Properties properties = new Properties();
        properties.setProperty("ID", "integer");
        properties.setProperty("USERNAME", "string");
        properties.setProperty("PASSWORD", "string");
        properties.setProperty("ROLE_ID", "integer");
        properties.setProperty("CREATED_AT", "timestamp");
        return properties;
    }

    @Override
    public String toString() {
        return String.format("%s:%s\n%s:%s\n%s:%s",
                "Username", this.getUsername(),
                "Password", this.getPassword(),
                "Role ID", this.getRoleID()
                );
    }

    @Override
    public User getByID(int id) {
        try{
            PreparedStatement preparedStatement =
                    Db.getInstance().getConnection().prepareStatement("SELECT * FROM USERS WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("ID"));
                user.setUsername(resultSet.getString("USERNAME"));
                user.setPassword(resultSet.getString("PASSWORD"));
                user.setRoleID(resultSet.getInt("ROLE_ID"));
                return user;
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return null;

    }

    @Override
    public List<User> getAll() {
        try{
            Statement statement = Db.getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");
            ArrayList<User> users = new ArrayList<>();
            while (resultSet.next()){
                users.add(
                        new User(
                                resultSet.getInt("ID"),
                                resultSet.getString("USERNAME"),
                                resultSet.getString("PASSWORD"),
                                resultSet.getInt("ROLE_ID")
                        )
                );
            }

            return users;

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return null;

    }

    @Override
    public boolean save() {
        if(this.isNewRecord()){
            try{
                Properties properties = new Properties();
                properties.setProperty("USERNAME", this.getUsername());
                properties.setProperty("PASSWORD", this.getPassword());
                properties.setProperty("ROLE_ID", String.valueOf(this.getRoleID()));

                return Db.getInstance().insert(this.getTableName(), this.getAttributeTypes(), properties);

            }catch (SQLException e){
                System.err.println(e.getMessage());
            }
        }else{
            try{
                Properties properties = new Properties();
                properties.setProperty("USERNAME", this.getUsername());
                properties.setProperty("PASSWORD", this.getPassword());
                properties.setProperty("ROLE_ID", String.valueOf(this.getRoleID()));
                Db.getInstance().update(this.getTableName(), this.getAttributeTypes(), properties, this.getId());
            }catch (SQLException e){
                System.err.println(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean delete() {
        if(!isNewRecord()){
            try{
                PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                        "DELETE FROM USERS WHERE ID = ?"
                );
                preparedStatement.setInt(1, this.getId());

                if(preparedStatement.executeUpdate() > 0){
                    return true;
                }

            }catch (SQLException e){
                System.err.println(e.getMessage());
            }
        }
        return false;
    }
}
