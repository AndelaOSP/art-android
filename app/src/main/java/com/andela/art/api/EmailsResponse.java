package com.andela.art.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Emails response pojo.
 */
public class EmailsResponse {

    @SerializedName("emails")
    @Expose
    private List<String> emails;

    /**
     *
     * @return emails - list
     */
    public List<String> getEmails() {
        return emails;
    }
}
