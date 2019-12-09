package models;

import helpers.Md5Converter;
import javafx.util.converter.ByteStringConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserModel {

    private String username;

    private String password;

    private int role_id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username.isEmpty()){
            throw new IllegalArgumentException("Username can not be empty");
        }

        Pattern pattern = Pattern.compile("<^\\w+$>");
        Matcher matcher = pattern.matcher(username);

        if(!matcher.find()){
            throw new IllegalArgumentException("Username should consist of letters and digits only");
        }

        this.username = username;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(username.isEmpty()){
            throw new IllegalArgumentException("Password can not be empty");
        }

        Pattern pattern = Pattern.compile("<^\\w+$>");
        Matcher matcher = pattern.matcher(password);

        if(!matcher.find()){
            throw new IllegalArgumentException("Password should consist of letters and digits only");
        }

        this.password = Md5Converter.hash(password);
    }

    public int getRoleID() {
        return role_id;
    }

    public void setRoleID(int role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return String.format("%s:%s\n%s:%s\n%s:%s",
                "Username", this.getUsername(),
                "Password", this.getPassword(),
                "Role ID", this.getRoleID()
                );
    }
}
