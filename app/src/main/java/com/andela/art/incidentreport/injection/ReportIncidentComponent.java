package com.andela.art.incidentreport.injection;

import com.andela.art.api.ApiModule;
import com.andela.art.firebase.FirebaseModule;
import com.andela.art.incidentreport.presentation.IncidentReportActivity;
import com.andela.art.root.Activity;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;

import dagger.Component;

/**
 * Incident report Component.
 */
@Activity
@Component(dependencies = {ApplicationComponent.class},
        modules = {
                ApplicationModule.class,
                ApiModule.class,
               IncidentReportModule.class,
                FirebaseModule.class})
public interface ReportIncidentComponent {

    /**
     * Inject to Incident report activity.
     * @param activity - activity to inject to
     */
    void inject(IncidentReportActivity activity);
}
