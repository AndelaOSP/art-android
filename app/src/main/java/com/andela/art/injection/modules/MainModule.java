package com.andela.art.injection.modules;

import com.andela.art.injection.scope.PerActivity;
import com.andela.art.main.MainView;

import dagger.Module;
import dagger.Provides;


@PerActivity
@Module
public class MainModule {
    private MainView mView;

    public MainModule(MainView view) {
        mView = view;
    }

    @PerActivity
    @Provides
    MainView provideContext() {
        return mView;
    }
}
