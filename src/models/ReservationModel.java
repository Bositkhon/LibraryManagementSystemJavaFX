package models;

import entities.Reservation;
import entities.User;
import helpers.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReservationModel implements ModelInterface <Reservation> {

    @Override
    public Reservation getById(int id) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM RESERVATIONS WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        return extractEntity(resultSet);
    }

    @Override
    public List<Reservation> getAll() throws SQLException {
        List <Reservation> reservations = new ArrayList<>();
        Statement statement = Db.getInstance().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM RESERVATIONS");
        Reservation reservation = extractEntity(resultSet);
        while (reservation != null){
            reservations.add(reservation);
            reservation = extractEntity(resultSet);
        }
        return reservations;
    }

    @Override
    public ResultSet getAllAsQueryResult() throws SQLException {
        return Db.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM RESERVATIONS");
    }

    @Override
    public Reservation extractEntity(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            Reservation reservation = new Reservation();
            reservation.setId(resultSet.getInt("ID"));
            reservation.setUserId(resultSet.getInt("USER_ID"));
            reservation.setBookId(resultSet.getInt("BOOK_ID"));
            reservation.setReserveDate(resultSet.getDate("RESERVE_DATE"));
            reservation.setCreatedAt(resultSet.getTimestamp("CREATED_AT"));
            return reservation;
        }
        return null;
    }

    @Override
    public boolean insert(Reservation model) throws SQLException {
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "INSERT INTO RESERVATIONS (USER_ID, BOOK_ID, RESERVE_DATE) VALUES (?, ?, ?)"
            );

            preparedStatement.setInt(1, model.getUserId());
            preparedStatement.setInt(2, model.getBookId());
            preparedStatement.setDate(3, model.getReserveDate());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean update(Reservation model) throws SQLException {
        if(model.validate()) {
            PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                    "UPDATE RESERVATIONS SET USER_ID = ?, BOOK_ID = ?, RESERVE_DATE = ? WHERE ID = ?"
            );

            preparedStatement.setInt(1, model.getUserId());
            preparedStatement.setInt(2, model.getBookId());
            preparedStatement.setDate(3, model.getReserveDate());
            preparedStatement.setInt(4, model.getId());

            return preparedStatement.executeUpdate() > 0;
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "DELETE FROM RESERVATIONS WHERE ID = ?"
        );
        preparedStatement.setInt(1, id);

        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public List<Reservation> getAllByCondition(Properties properties) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String pairs = "";
        for(String key : properties.stringPropertyNames()){
            pairs += String.format("%s %s", key, properties.getProperty(key));
        }
        String query = String.format("SELECT * FROM RESERVATIONS %s", pairs);
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            reservations.add(extractEntity(resultSet));
        }
        return reservations;
    }

    public List<Reservation> getAllByUserId(int id) throws SQLException{
        List <Reservation> reservations = new ArrayList<>();
        PreparedStatement preparedStatement = Db.getInstance().getConnection().prepareStatement(
                "SELECT * FROM RESERVATIONS WHERE USER_ID = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Reservation reservation = extractEntity(resultSet);
        while (reservation != null){
            reservations.add(reservation);
            reservation = extractEntity(resultSet);
        }
        return reservations;
    }

}
