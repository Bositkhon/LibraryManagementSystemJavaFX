package models;

public class IssuedBookModel {

    private enum Status{
        ISSUED(1),
        FINED(2);

        private final int id;

        Status(int id){
            this.id = id;
        }

    };

    private Status status;

    private int user_id;

    private int book_id;

    private int period_id;

    private String due;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public int getPeriodId() {
        return period_id;
    }

    public void setPeriodId(int period_id) {
        this.period_id = period_id;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }
}
