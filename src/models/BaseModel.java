package models;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseModel {

    protected ArrayList<String> errors;

    protected boolean isNewRecord;

    public BaseModel(){
        this.isNewRecord = true;
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public void addError(String message){
        this.errors.add(message);
    }

    public boolean hasErrors(){
        return !this.errors.isEmpty();
    }

    public boolean validate(){
        return !this.hasErrors();
    };

    public boolean isNewRecord() {
        return isNewRecord;
    }

}
