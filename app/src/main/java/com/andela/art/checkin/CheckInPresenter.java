package com.andela.art.checkin;

import com.andela.art.checkin.data.CheckInService;
import com.andela.art.checkin.data.ServiceBuilder;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class CheckInPresenter {

    CheckInView view;
    CheckInActivity activity;

    public CheckInPresenter(CheckInActivity activity, CheckInView view) {
        this.activity = activity;
        this.view = view;
    }

    /**
     * Calls API service to hit check in endpoint.
     */
    public void checkIn(String serialNumber) {
        CheckInService mCheckInService = ServiceBuilder.buildService(CheckInService.class);
        Call<Observable> checkin = mCheckInService.checkIn(serialNumber);
        checkin.enqueue(new Callback<Observable>() {
            @Override
            public void onResponse(Call<Observable> call, Response<Observable> response) {
                activity.showCheckout();
            }

            @Override
            public void onFailure(Call<Observable> call, Throwable t) {

            }
        });
    }
}
