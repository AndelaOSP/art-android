package com.andela.art.settings.injection;


import com.andela.art.api.ApiModule;
import com.andela.art.firebase.FirebaseModule;
import com.andela.art.root.Activity;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.settings.presentation.ReportProblemActivity;

import dagger.Component;

/**
 * Created by chadwalt on 4/16/18.
 */
@Activity
@Component(dependencies = {ApplicationComponent.class},
        modules = {
                ApiModule.class,
                ReportProblemModule.class,
                FirebaseModule.class,
        })
public interface ReportProblemComponent {
    /**
     * Inject report problem presenter into the report problem activity.
     *
     * @param reportProblemActivity activity where report problem presenter will be injected.
     */
    void inject(ReportProblemActivity reportProblemActivity);

}
