package com.andela.art.root;

import com.andela.art.api.ApiModule;
import com.andela.art.firebase.FirebaseModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by zack on 3/5/18.
 */
@Singleton @Component(modules = {ApplicationModule.class, ApiModule.class, FirebaseModule.class})
public interface ApplicationComponent {

    /**
     * Builder instance for application component.
     */
    @Component.Builder
    interface Builder {

        /**
         * Bind application instance.
         *
         * @param artApplication application instance to be used by builder
         * @return Builder
         */
        @BindsInstance
        Builder application(ArtApplication artApplication);

        /**
         * Bind application module instance.
         * @param applicationModule application module
         * @return Builder
         */
        Builder applicationModule(ApplicationModule applicationModule);

        /**
         * Application component builder.
         * @return applicationcomponent
         */
        ApplicationComponent build();



    }
}
