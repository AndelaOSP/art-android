package com.andela.art.login;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

public class LoginModel implements LoginActivityMVP.LoginModel {
    private LoginRepository repository;

    public LoginModel(LoginRepository repo) {
        this.repository = repo;
    }
}
