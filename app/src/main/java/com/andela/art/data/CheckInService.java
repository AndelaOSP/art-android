package com.andela.art.data;

import com.andela.art.models.CheckinResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface CheckInService {

    @POST("/checkin")
    @FormUrlEncoded
    Observable<CheckinResponse> checkIn(@Field("serialNumber") String serialNumber);
}
