package com.andela.art.login;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

public class LoginRepository {

    private User user;

    public User getUser() {

        if (user == null) {
            User user = new User("Fox", "Mulder");
            user.setId(0);
            return user;
        } else {
            return user;

        }

    }

    public void saveUser(User user) {

        if (user == null) {
            user = getUser();
        }

        this.user = user;

    }
}
