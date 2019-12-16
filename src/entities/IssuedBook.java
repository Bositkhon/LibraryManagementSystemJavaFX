package entities;

import java.sql.Date;
import java.sql.Timestamp;

public class IssuedBook extends Entity{

    public enum Status{
        ISSUED(1, "Issued"),
        FINED(2, "Fined");

        private final int id;
        private final String title;

        Status(int id, String title){
            this.id = id;
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public static Integer getStatusIdByTitle(String title){
            if(title.equals(ISSUED.getTitle())){
                return ISSUED.getId();
            }else if(title.equals(FINED.getTitle())){
                return FINED.getId();
            }
            return null;
        }

        public static Integer getStatusId(Status status){
            if(status == ISSUED){
                return ISSUED.getId();
            }else if(status == FINED){
                return FINED.getId();
            }
            return null;
        }

        public static Status getStatusByTitle(String title){
            if(title.equals(ISSUED.getTitle())){
                return ISSUED;
            }else if(title.equals(FINED.getTitle())){
                return FINED;
            }
            return null;
        }

        public static String getStatusTitleById(int id){
            if(id == ISSUED.getId()){
                return ISSUED.getTitle();
            }else if(id == FINED.getId()){
                return FINED.getTitle();
            }
            return null;
        }

    };

    private Integer id;

    private Status status;

    private Integer user_id;

    private Integer book_id;

    private Integer period_id;

    private Date due;

    private Timestamp issued_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatus(int id){

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

    public Integer getPeriodId() {
        return period_id;
    }

    public void setPeriodId(Integer period_id) {
        this.period_id = period_id;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public Timestamp getIssuedAt() {
        return issued_at;
    }

    public void setIssuedAt(Timestamp issued_at) {
        this.issued_at = issued_at;
    }

    @Override
    public boolean validate() {
        return true;
    }
}
