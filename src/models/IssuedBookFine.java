package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IssuedBookFine {

    private int id;

    private int issued_book_id;

    private String reason;

    IssuedBookFine(){}

    IssuedBookFine(int issued_book_id, String reason){
        this.setIssuedBookId(issued_book_id);
        this.setReason(reason);
    }

    public int getId() {
        return id;
    }

    public int getIssuedBookId() {
        return issued_book_id;
    }

    public void setIssuedBookId(int issued_book_id) {
        this.issued_book_id = issued_book_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        if(reason.isEmpty()){
            throw new IllegalArgumentException("Reason can not be empty");
        }
        this.reason = reason;
    }
}
