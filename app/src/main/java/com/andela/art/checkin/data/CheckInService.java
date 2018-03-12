package com.andela.art.checkin.data;

import com.andela.art.checkin.models.CheckinResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface CheckInService {

    @POST("/checkins")
    @FormUrlEncoded
    Observable<CheckinResponse> checkIn(@Field("serial") String serialNumber);
}
