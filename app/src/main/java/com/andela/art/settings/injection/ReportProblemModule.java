package com.andela.art.settings.injection;

import com.andela.art.api.ApiService;
import com.andela.art.settings.presentation.ReportProblemPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chadwalt on 4/17/18.
 */
@Module
public class ReportProblemModule {
    /**
     * Provide Report Problem presenter instance.
     *
     * @param apiService api service to be used by the Report Problem Presenter.
     *
     * @return ReportProblemPresenter.
     */
    @Provides
    ReportProblemPresenter provideReportProblemPresenter(ApiService apiService) {
        return new ReportProblemPresenter(apiService);
    }
}
