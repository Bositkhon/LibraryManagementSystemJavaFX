package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IssuePeriod {

    private int id;

    private String title;

    private int days;

    IssuePeriod(){}

    IssuePeriod(String title, int days){
        this.setTitle(title);
        this.setDays(days);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title.isEmpty()){
            throw new IllegalArgumentException("Title can not be empty");
        }

        Pattern pattern = Pattern.compile("^\\w+$");
        Matcher matcher = pattern.matcher(title);

        if(!matcher.find()){
            throw new IllegalArgumentException("Title should consist of letters and digits only");
        }
        this.title = title;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
