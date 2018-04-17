package com.andela.art.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
