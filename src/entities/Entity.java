package entities;

import java.util.List;

public abstract class Entity {

    private boolean hasBeenValidated;

    private List<String> errors;

    public Entity(){
        this.hasBeenValidated = false;
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

    public abstract boolean validate();

}
