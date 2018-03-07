package com.andela.art.serialentry.data;

import io.reactivex.Single;

/**
 * Created by zack on 3/5/18.
 */

public interface AssetRespository {
    Single<Asset> getAsset(String serial);
}
