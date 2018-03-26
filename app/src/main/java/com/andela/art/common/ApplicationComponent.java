package com.andela.art.common;


import com.andela.art.api.ApiModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by zack on 3/5/18.
 */
@Singleton @Component(modules={ApiModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder{
        ApplicationComponent build();
        @BindsInstance Builder application(ArtApplication artApplication);
    }
}
