package com.andela.art.api;

import com.andela.art.model.CheckInModel;
import com.andela.art.model.CheckInResponse;
import com.andela.art.utils.Constants;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Check in service class.
 */
public interface CheckInService {

    /**
     * Check in route.
     * @param checkInModel - check in model
     * @return Observable
     */
    @POST(Constants.CHECK_IN)
    @FormUrlEncoded
    Observable<CheckInResponse> checkIn(@Body CheckInModel checkInModel);
}
