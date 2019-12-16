package entities;

import models.BookModel;
import models.UserModel;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reservation extends Entity {

    private Integer id;

    private Integer user_id;

    private Integer book_id;

    private Date reserve_date;

    private Timestamp created_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getBookId() {
        return book_id;
    }

    public void setBookId(Integer book_id) {
        this.book_id = book_id;
    }

    public Date getReserveDate() {
        return reserve_date;
    }

    public void setReserveDate(Date reserve_date) {
        this.reserve_date = reserve_date;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }

    public User getUser() throws SQLException {
        UserModel userModel = new UserModel();
        return userModel.getById(this.getUserId());
    }

    public Book getBook() throws SQLException{
        BookModel bookModel = new BookModel();
        return bookModel.getById(this.getBookId());
    }

    @Override
    public boolean validate() throws SQLException {
        boolean valid = true;
        if(this.getUser() == null){
            this.addError("User with such ID doesn't exists");
            valid = false;
        }
        if(this.getBookId() == null){
            this.addError("Book with such ID doesn't exists");
            valid = false;
        }
        return valid;
    }
}
