package com.andela.art.common;


import com.andela.art.serialentry.data.AssetRespository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zack on 3/5/18.
 */
@Singleton @Component(modules={ApplicationModule.class,DataModule.class})
public interface ApplicationComponent {

    AssetRespository assetRepository();
}
