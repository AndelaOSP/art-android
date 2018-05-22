package com.andela.art.incidentreport.injection;

import com.andela.art.api.ApiService;
import com.andela.art.incidentreport.presentation.IncidentReportPresenter;
import com.andela.art.root.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Incident Report module.
 */
@Module
public class IncidentReportModule {

    /**
     * Provide Incident report activity.
     * @param apiService - ApiService instance
     *
     *  @return IncidentReportPresenter
     */
    @Activity
    @Provides
    IncidentReportPresenter provideIncidentReportPresenter(ApiService apiService) {
        return  new IncidentReportPresenter(apiService);
    }
}
