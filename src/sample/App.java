package sample;

import entities.User;

public class App {

    private User loggedUser;

    App(){
        this.loggedUser = null;
    }

    public void login(User userModel){
        this.loggedUser = userModel;
    }

    public void logout(){
        this.loggedUser = null;
    }

    public boolean isLoggedIn(){
        return this.loggedUser != null;
    }

    public User getLoggedUser(){
        return this.loggedUser;
    }

}
