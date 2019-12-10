package models;

import java.text.DateFormat;

public class Reservation {

    private int id;

    private int user_id;

    private int book_id;

    private String reserve_date;

    Reservation(){}

    Reservation(int user_id, int book_id, String reserve_date){
        this.setUserId(user_id);
        this.setBookId(book_id);
        this.setReserveDate(reserve_date);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public int getBookId() {
        return book_id;
    }

    public void setBookId(int book_id) {
        this.book_id = book_id;
    }

    public String getReserveDate() {
        return reserve_date;
    }

    public void setReserveDate(String reserve_date) {
        this.reserve_date = reserve_date;
    }
}
