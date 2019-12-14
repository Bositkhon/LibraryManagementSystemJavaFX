package sample;

import models.User;

public class App {

    private User loggedUser;

    App(){
        this.loggedUser = null;
    }

    public void login(User user){
        this.loggedUser = user;
    }

    public void logout(){
        this.loggedUser = null;
    }

    public User getCurrentUser() {
        return this.loggedUser;
    }

}
