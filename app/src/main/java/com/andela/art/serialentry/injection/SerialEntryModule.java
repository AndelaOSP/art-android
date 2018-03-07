package com.andela.art.serialentry.injection;

import com.andela.art.serialentry.data.AssetRespository;
import com.andela.art.serialentry.domain.GetAssetUseCase;
import com.andela.art.serialentry.presentation.SerialPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zack on 3/5/18.
 */

@Module
public class SerialEntryModule {

    @Activity
    @Provides
    GetAssetUseCase provideGetAssetUseCase(AssetRespository assetRespository){
        return new GetAssetUseCase(assetRespository);
    }
    @Activity
    @Provides
    SerialPresenter provideSerialPresenter(GetAssetUseCase getAssetUseCase){
        return new SerialPresenter(getAssetUseCase);
    }
}
