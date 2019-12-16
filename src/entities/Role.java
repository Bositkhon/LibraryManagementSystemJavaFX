package entities;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Role extends Entity{

    private Integer id;

    private String title;

    private Timestamp created_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

        Pattern pattern = Pattern.compile("\\w+$");
        Matcher matcher = pattern.matcher(this.getTitle());

        if(this.getTitle().isEmpty()){
            this.addError("Title can not be empty");
            valid = false;
        }else if(!matcher.find()){
            this.addError("Title should consist of letters and digits only");
            valid = false;
        }

        return valid;
    }
}
