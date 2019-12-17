package entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity {

    protected boolean hasBeenValidated;

    protected List<String> errors;

    public Entity(){
        this.hasBeenValidated = false;
        this.errors = new ArrayList<>();
    }

    public boolean hasErrors(){
        return this.errors.isEmpty();
    }

    public void addError(String message){
        this.errors.add(message);
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public abstract boolean validate() throws SQLException;

}
