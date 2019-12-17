package sample;

import models.UserModel;

public class App {

    private UserModel loggedUserModel;

    App(){
        this.loggedUserModel = null;
    }

    public void login(UserModel userModel){
        this.loggedUserModel = userModel;
    }

    public void logout(){
        this.loggedUserModel = null;
    }

    public UserModel getCurrentUser() {
        return this.loggedUserModel;
    }

}
