package com.andela.art.common;

import com.andela.art.serialentry.data.AssetRepositoryImpl;
import com.andela.art.serialentry.data.AssetRespository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zack on 3/5/18.
 */

@Module
public class ApplicationModule {

    private ArtApplication artApplication;

    public ApplicationModule(ArtApplication artApplication) {
        this.artApplication = artApplication;
    }

    @Singleton
    @Provides
    ArtApplication providesArtApplication() {
        return artApplication;
    }
}
