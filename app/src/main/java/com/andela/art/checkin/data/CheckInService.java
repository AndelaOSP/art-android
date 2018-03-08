package com.andela.art.checkin.data;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface CheckInService {

    @POST("/checkin")
    @FormUrlEncoded
    Call<Observable> checkIn(@Field("serialNumber") String serialNumber);
}
