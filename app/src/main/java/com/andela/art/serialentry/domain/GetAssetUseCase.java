package com.andela.art.serialentry.domain;

import com.andela.art.serialentry.data.Asset;
import com.andela.art.serialentry.data.AssetRespository;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by zack on 3/5/18.
 */

public class GetAssetUseCase extends UseCase<Asset> {

    private  AssetRespository assetRespository;

    private String serial;

    public GetAssetUseCase(AssetRespository assetRespository) {
        this.assetRespository = assetRespository;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Override
    public Single<Asset> buildObservable() {
        return assetRespository.getAsset(serial);
    }
}
