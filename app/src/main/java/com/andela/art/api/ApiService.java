package com.andela.art.api;

import com.andela.art.serialentry.data.Asset;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zack on 3/5/18.
 * ApiService for with asset retrieval endpoint.
 */

public interface ApiService {
    /**
     * Get asset method.
     *
     * @param serial serial to be searched
     * @return Observable
     */
    @GET("/assets")
    Observable<Asset> getAsset(@Query("serial_number") String serial);
}
