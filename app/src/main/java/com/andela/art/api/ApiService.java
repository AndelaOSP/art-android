package com.andela.art.api;

import com.andela.art.models.Asset;
import com.andela.art.models.CheckInModel;
import com.andela.art.models.IncidentModel;
import com.andela.art.models.ReportProblem;
import com.andela.art.models.SendFeedback;

import io.reactivex.Observable;
import retrofit2.http.Body;
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
     * Get all assets method.
     *
     * @return Observable
     */
    @GET("/api/v1/assets/")
    Observable<UserAssetResponse> getAssets();

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
    Observable<CheckInModel> checkIn(@Field("asset") String serialNumber,
                                     @Field("log_type") String logType);

    /**
     * Report a problem.
     *
     * @param reportedBy the person submitting the report
     * @param message the report to submit
     * @param reportType the type of report (bug)
     *
     * @return Observable
     */
    @FormUrlEncoded
    @POST("/api/v1/user-feedback/")
    Observable<ReportProblem> reportProblem(
            @Field("reported_by") String reportedBy,
            @Field("message") String message,
            @Field("report_type") String reportType
    );

    /**
     * Send feedback.
     *
     * @param reportedBy the person sending the report
     * @param message feedback to send
     * @param reportType the type of report (feedback)
     *
     * @return Observable
     */
    @FormUrlEncoded
    @POST("/api/v1/user-feedback/")
    Observable<SendFeedback> sendFeedback(
            @Field("reported_by") String reportedBy,
            @Field("message") String message,
            @Field("report_type") String reportType
    );

    /**
     * Send incident report.
     * @param incidentModel - incident model object
     *
     * @return Observable
     */
    @POST("/api/v1/incidence-reports")
    Observable<IncidentModel> reportIncident(
           @Body IncidentModel incidentModel
    );
}
