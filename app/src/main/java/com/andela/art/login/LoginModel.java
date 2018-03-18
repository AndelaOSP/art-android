package com.andela.art.login;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

public class LoginModel implements LoginActivityMVP.LoginModel {
    private final LoginRepository repository;

    public LoginModel(LoginRepository repo) {
        this.repository = repo;
    }
}
