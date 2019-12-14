package models;

import helpers.Db;
import interfaces.DataAccessInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Role extends BaseModel implements DataAccessInterface<Role> {

    private int id;

    private String title;

    public Role(){
        super();
    }

    Role(String title){
        this.setTitle(title);
    }

    protected Role(int id, String title){
        this.setId(id);
        this.setTitle(title);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title.isEmpty()){
            throw new IllegalArgumentException("Title can not be empty");
        }

        Pattern pattern = Pattern.compile("^\\w+$");
        Matcher matcher = pattern.matcher(title);

        if(!matcher.find()){
            throw new IllegalArgumentException("Title should consist of letters and digits only");
        }
        this.title = title;
    }

    public Properties getAttributeTypes(){
        Properties properties = new Properties();
        properties.setProperty("ID", "integer");
        properties.setProperty("TITLE", "string");
        properties.setProperty("CREATED_AT", "timestamp");
        return properties;
    }

    @Override
    public String getTableName() {
        return "ROLES";
    }

    @Override
    public String toString() {
        return String.format("%s:%s\n",
                "Title", this.getTitle()
        );
    }

    @Override
    public Role getByID(int id) {
        try{
            PreparedStatement preparedStatement =
                    Db.getInstance().getConnection().prepareStatement("SELECT * FROM ROLES WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Role role = new Role();
                role.setId(resultSet.getInt("ID"));
                role.setTitle(resultSet.getString("Title"));
                return role;
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Role> getAll() {
        try{
            Statement statement = Db.getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ROLES");
            ArrayList<Role> roles = new ArrayList<>();
            while (resultSet.next()){
                roles.add(
                        new Role(
                                resultSet.getInt("ID"),
                                resultSet.getString("TITLE")
                        )
                );
            }

            return roles;

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
                properties.setProperty("TITLE", this.getTitle());
                return Db.getInstance().insert(this.getTableName(), this.getAttributeTypes(), properties);

            }catch (SQLException e){
                System.err.println(e.getMessage());
            }
        }else{
            try{
                Properties properties = new Properties();
                properties.setProperty("TITLE", this.getTitle());
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
                        "DELETE FROM ROLES WHERE ID = ?"
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
