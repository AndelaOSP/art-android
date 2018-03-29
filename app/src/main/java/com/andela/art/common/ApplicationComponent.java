package com.andela.art.common;



import com.andela.art.api.ApiModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by zack on 3/5/18.
 */
@Singleton @Component(modules = {ApiModule.class})
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
         * Application component builder.
         * @return applicationcomponent
         */
        ApplicationComponent build();



    }
}
