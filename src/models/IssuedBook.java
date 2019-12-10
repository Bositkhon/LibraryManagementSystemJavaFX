package models;

public class IssuedBook {

    public enum Status{
        ISSUED(1, "Issued"),
        FINED(2, "Fined");

        private final int id;

        private final String name;

        Status(int id, String name){
            this.id = id;
            this.name = name;
        }

    };

    IssuedBook(){}

    IssuedBook(Status status, int user_id, int book_id, int period_id, String due){
        this.setStatus(status);
        this.setUserId(user_id);
        this.setBookId(book_id);
        this.setPeriodId(period_id);
        this.setDue(due);
    }

    private int id;

    private Status status;

    private int user_id;

    private int book_id;

    private int period_id;

    private String due;

    public int getId() {
        return id;
    }

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
