package helpers;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

public class Db {

    private static Db instance;
    private Connection connection;
    private String url = "jdbc:derby:LibraryManagementSystemDB";
    private String username = "Bositkhon";
    private String password = "1q2w3e4r5t";

    private Db() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Db getInstance() throws SQLException {
        if (instance == null) {
            instance = new Db();
        } else if (instance.getConnection().isClosed()) {
            instance = new Db();
        }

        return instance;

    }

    public boolean insert(String tableName, Properties columnTypes, Properties columnValues){
        try{
            String columns = "", values = "";

            for(String key : columnValues.stringPropertyNames()){
                columns += key + ",";
                values += "?,";
            }
            columns = columns.substring(0, columns.length() - 1);
            values = values.substring(0, values.length() - 1);

            String queryTemplate = String.format("INSERT INTO %s (%s) VALUES (%s)",
                    tableName, columns, values);

            PreparedStatement preparedStatement = this.getConnection().prepareStatement(queryTemplate);
            preparedStatement.setString(1, tableName);
            Set<String> colTypeKeys = columnTypes.stringPropertyNames(), colValueKeys = columnValues.stringPropertyNames();
            System.out.println(queryTemplate);
            int n = 1;
            for(String key : colValueKeys){
                if(colTypeKeys.contains(key)){
                    if(columnTypes.getProperty(key).equals("string")){
                        preparedStatement.setString(n, columnValues.getProperty(key));
                    }else if(columnTypes.getProperty(key).equals("integer")){
                        preparedStatement.setInt(n, Integer.parseInt(columnValues.getProperty(key)));
                    }else if(columnTypes.getProperty(key).equals("float")){
                        preparedStatement.setFloat(n, Float.parseFloat(columnValues.getProperty(key)));
                    }else if(columnTypes.getProperty(key).equals("double")){
                        preparedStatement.setDouble(n, Double.parseDouble(columnValues.getProperty(key)));
                    }
                }
                n++;
            }

            if(preparedStatement.executeUpdate() > 0){
                return true;
            }

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return false;
    }

    public boolean update(String tableName, Properties colTypes, Properties colValues, int id){
        try{
            String keyValues = "";

            Set<String> colTypeKeys = colTypes.stringPropertyNames(), colValueKeys = colValues.stringPropertyNames();

            for(String key : colValueKeys){
                if(colTypeKeys.contains(key)){
                    keyValues += key + " = " + "?,";
                }
            }

            keyValues = keyValues.substring(0, keyValues.length() - 1);

            String queryTemplate = String.format("UPDATE %s SET %s WHERE ID = ?",
                    tableName, keyValues);

            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(queryTemplate);
            int n = 1;
            for(String key : colValueKeys){
                if(colTypeKeys.contains(key)){
                    if(colTypes.getProperty(key).equals("string")){
                        preparedStatement.setString(n, colValues.getProperty(key));
                    }else if(colTypes.getProperty(key).equals("integer")){
                        preparedStatement.setInt(n, Integer.parseInt(colValues.getProperty(key)));
                    }else if(colTypes.getProperty(key).equals("double")){
                        preparedStatement.setDouble(n, Double.parseDouble(colValues.getProperty(key)));
                    }else if(colTypes.getProperty(key).equals("float")){
                        preparedStatement.setFloat(n, Float.parseFloat(colValues.getProperty(key)));
                    }
                }
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ResultSet read(String tableName, String[] columns, Properties properties, String... arguments) {
        try{
            String columnPlaceholders = "";
            for(int i = 0; i < columns.length; i++){
                columnPlaceholders += "?,";
            }
            columnPlaceholders = columnPlaceholders.substring(0, columnPlaceholders.length() - 1);

            for(String key : properties.stringPropertyNames()){
                
            }

            String queryTemplate = String.format(
                    "SELECT %s FROM %s %s",
                    columnPlaceholders, tableName);
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    queryTemplate
            );
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ResultSet readColumns(String tableName, String[] columns){
        try {
            String placeholder = "";
            for(String columnName : columns){
                placeholder += "?,";
            }
            placeholder = placeholder.substring(0, placeholder.length() - 1);

            String queryTemplate = String.format("SELECT %s FROM %s", placeholder, tableName);

            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    queryTemplate
            );
            int n = 1;
            for(String column : columns){
                preparedStatement.setString(n, column);
                n++;
            }

            return preparedStatement.executeQuery();

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return null;
    }

    public ResultSet readColumn(String tableName, String column){
        try {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "SELECT ? FROM ?"
            );
            preparedStatement.setString(1, column);
            preparedStatement.setString(2, tableName);
            return preparedStatement.executeQuery();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ResultSet readAll(String tableName){
        try{
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "SELECT * FROM ?"
            );
            preparedStatement.setString(1, tableName);
            return preparedStatement.executeQuery();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return null;
    }

}