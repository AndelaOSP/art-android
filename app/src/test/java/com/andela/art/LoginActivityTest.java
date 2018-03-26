package com.andela.art;

import org.junit.Test;

import static com.andela.art.login.LoginActivity.isAllowedNonAndelaEmail;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by allan on 27/03/2018.
 */

public class LoginActivityTest {

    /**
     * Test if a provided Non-Andelan email address is allowed or disallowed.
     * correctly
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void non_andela_emails_allowed_correctly() throws Exception {
        assertTrue(isAllowedNonAndelaEmail("muhallan1@gmail.com"));
        assertFalse(isAllowedNonAndelaEmail("eric_elem@gmail.com"));
    }
}
