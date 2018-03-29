package com.andela.art.login;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

public class LoginRepository {

    private User user;

    /**
     * Get user, create new user if null else return existing user.
     *
     * @return user - Created user/Existing user.
     */
    public User getUser() {

        if (user == null) {
            User user = new User();
            user.setId();
            return user;
        } else {
            return user;

        }

    }

    /**
     * Save user.
     *
     * @param user - user specified.
     */
    public void saveUser(User user) {

        if (user == null) {
            user = getUser();
        }

        this.user = user;

    }
}
