package com.andela.art.login;

import com.andela.art.root.View;

import java.util.List;

/**
 * Created by zack on 4/16/18.
 */

public interface SecurityEmailsView extends View {
    /**
     *
     * @param emails - list of emails.
     */
    void populateEmailList(List<String> emails);
}
