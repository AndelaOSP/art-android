package com.andela.art.reportproblem.injection;


import com.andela.art.api.ApiModule;
import com.andela.art.firebase.FirebaseModule;
import com.andela.art.reportproblem.presentation.ReportProblemActivity;
import com.andela.art.root.Activity;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;

import dagger.Component;

/**
 * Created by chadwalt on 4/16/18.
 */
@Activity
@Component(dependencies = {ApplicationComponent.class},
        modules = {
                ApplicationModule.class,
                ApiModule.class,
                com.andela.art.reportproblem.injection.ReportProblemModule.class,
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
