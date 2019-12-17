package entities;
import helpers.Db;
import models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User extends Entity {

    private Integer id;

    private String username;

    private String password;

    private Integer role_id;

    private Timestamp created_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return role_id;
    }

    public void setRoleId(Integer role_id) {
        this.role_id = role_id;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Role getRole() throws SQLException {
        RoleModel roleModel = new RoleModel();
        return roleModel.getById(this.getRoleId());
    }

    public List <IssuedBook> getIssuedBooks() throws SQLException{
        IssuedBookModel issuedBookModel = new IssuedBookModel();
        return issuedBookModel.getByUserId(this.getId());
    }

    public List <Reservation> getReservations() throws SQLException{
        ReservationModel reservationModel = new ReservationModel();
        return reservationModel.getByUserId(this.getId());
    }

    @Override
    public boolean validate() throws SQLException{
        boolean valid = true;

        Pattern pattern = Pattern.compile("\\w+$");
        Matcher matcher = pattern.matcher(this.getUsername());

        UserModel userModel = new UserModel();
        User user = userModel.getByUsernameAndPassword(this.getUsername());
        if(user != null){
            this.addError("User with this username and password already exists");
            valid = false;
        }

        if(this.getUsername().isEmpty()){
            this.addError("Username can not be empty");
            valid = false;
        }else if(!matcher.find()){
            this.addError("Username should consist of letters and digits only");
            valid = false;
        }

        if(this.getPassword().isEmpty()){
            this.addError("Password can not be empty");
            valid = false;
        }

        if(this.getRole() == null){
            this.addError("Role with such ID doesn't exists");
            valid = false;
        }
        return valid;
    }

}