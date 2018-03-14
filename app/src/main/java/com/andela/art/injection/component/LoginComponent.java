package com.andela.art.injection.component;

import com.andela.art.injection.scope.PerActivity;
import com.andela.art.login.LoginActivity;
import com.andela.art.login.LoginModule;
import com.andela.art.root.ApplicationComponent;

import dagger.Component;

/**
 * Created by godwingitonga on 14/03/2018.
 */

@Component(modules = LoginModule.class, dependencies = ApplicationComponent.class)
@PerActivity
public interface LoginComponent {
    void inject(LoginActivity target);
}
