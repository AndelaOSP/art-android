package com.andela.art.login;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zack on 4/17/18.
 */

public class LoginActivity extends AppCompatActivity implements SecurityEmailsView {

    /**
     * Check to see if a given non Andela GMail address is among the ones allowed to login.
     *
     * @param email Email address for the user
     * @return boolean true if an email is allowed, false if the email is not allowed
     */
    public boolean isAllowedNonAndelaEmail(String email) {
        List<String> allowedEmailAddresses = new ArrayList<>();
        allowedEmailAddresses.add("muhallan1@gmail.com");
        allowedEmailAddresses.add("chadwalt04@gmail.com");

        return allowedEmailAddresses.contains(email);


    }

    /**
     * @param emails - list of emails.
     */
    @Override
    public void populateEmailList(List<String> emails) {

    }
}
