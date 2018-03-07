package com.andela.art.serialentry.data;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zack on 3/5/18.
 */

public interface ApiService {
    @GET("/assets")
    Single<Asset> getAsset(@Query("serial_number") String serial);
}
