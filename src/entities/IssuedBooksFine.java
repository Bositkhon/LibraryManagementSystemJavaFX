package entities;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IssuedBooksFine extends Entity{

    private Integer id;

    private Integer issued_book_id;

    private String reason;

    private Timestamp fined_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIssuedBookId() {
        return issued_book_id;
    }

    public void setIssuedBookId(Integer issued_book_id) {
        this.issued_book_id = issued_book_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getFinedAt() {
        return fined_at;
    }

    public void setFinedAt(Timestamp fined_at) {
        this.fined_at = fined_at;
    }

    @Override
    public boolean validate() {
        return true;
    }
}
