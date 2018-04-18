package com.andela.art.api;

import com.andela.art.models.Asset;
import com.andela.art.models.CheckInModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @GET("/api/v1/assets/{serial_number}/")
    Observable<Asset> getAsset(@Path("serial_number") String serial);

    /**
     * Fetch oauth token to be used for fetching security users emails.
     * @param grantType - grantType
     * @param clientId - clientId
     * @param clientSecret - clientSecret
     * @return Observable
     */
    @FormUrlEncoded
    @POST("/api/v1/o/token/")
    Observable<TokenResponse> fetchOauthToken(@Field("grant_type") String grantType,
                                       @Field("client_id")String clientId,
                                       @Field("client_secret")String clientSecret);

    /**
     * Fetch emails of security users.
     * @return Observable
     */
    @GET("/api/v1/security-user-emails/")
    Observable<EmailsResponse> getEmails();

    /**
     * Check in route.
     * @param serialNumber - asset serial number
     * @param logType - check in status
     * @return Observable
     */
    @POST("/api/v1/asset-logs/")
    @FormUrlEncoded
    Observable<CheckInModel> checkIn(@Field("serial_number") String serialNumber,
                                     @Field("log_type") String logType);
}
