package com.andela.art.api;

import com.andela.art.securitydashboard.data.Asset;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
    @GET("/assets/{serial_number}/")
    Observable<Asset> getAsset(@Path("serial_number") String serial);
}
