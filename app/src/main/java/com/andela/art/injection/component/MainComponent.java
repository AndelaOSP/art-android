package com.andela.art.injection.component;

import com.andela.art.injection.modules.MainModule;
import com.andela.art.injection.scope.PerActivity;
import com.andela.art.main.MainActivity;

import dagger.Component;


@Component(modules = MainModule.class, dependencies = ApplicationComponent.class)
@PerActivity
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
