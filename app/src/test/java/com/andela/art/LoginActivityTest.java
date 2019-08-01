package com.andela.art;

import com.andela.art.login.LoginActivity;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by allan on 27/03/2018.
 */

public class LoginActivityTest {
    LoginActivity loginActivity;

    /**
     * Test if a provided Non-Andelan email address is allowed or disallowed.
     * correctly
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void non_andela_emails_allowed_correctly() throws Exception {
        loginActivity = new LoginActivity();
        assertTrue(loginActivity.isAllowedNonAndelaEmail("muhallan1@gmail.com"));
        assertFalse(loginActivity.isAllowedNonAndelaEmail("eric_elem@gmail.com"));
    }
}
