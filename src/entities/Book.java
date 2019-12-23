package entities;

import java.sql.Date;
import java.sql.Timestamp;

public class Book extends Entity {

    private Integer id;

    private Integer quantity;

    private String title;

    private String subject;

    private String author;

    private String isbn;

    private Date published_date;

    private Timestamp created_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getPublishedDate() {
        return published_date;
    }

    public void setPublishedDate(Date published_date) {
        this.published_date = published_date;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean validate() {
        boolean valid = true;
        if (this.getQuantity() < 0) {
            this.addError("Quantity should be a positive number");
            valid = false;
        }
        if (this.getAuthor().isEmpty()) {
            this.addError("Author can not be empty");
            valid = false;
        }
        if (this.getIsbn().isEmpty()) {
            this.addError("ISBN can not be empty");
            valid = false;
        }
        if (this.getSubject().isEmpty()) {
            this.addError("Subject can not be empty");
            valid = false;
        }
        if (this.getTitle().isEmpty()) {
            this.addError("Title can not be empty");
            valid = false;
        }
        if(this.published_date == null){
            this.addError("Published date can not be empty");
            valid = false;
        }
        return valid;
    }
}
