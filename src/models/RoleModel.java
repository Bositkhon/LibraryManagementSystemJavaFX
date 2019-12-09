package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoleModel {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title.isEmpty()){
            throw new IllegalArgumentException("Title can not be empty");
        }

        Pattern pattern = Pattern.compile("<^\\w+$>");
        Matcher matcher = pattern.matcher(title);

        if(!matcher.find()){
            throw new IllegalArgumentException("Title should consist of letters and digits only");
        }
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("%s:%s\n",
                "Title", this.getTitle()
        );
    }
}
