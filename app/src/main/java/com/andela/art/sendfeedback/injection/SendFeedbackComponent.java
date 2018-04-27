package com.andela.art.sendfeedback.injection;

import com.andela.art.api.ApiModule;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.securitydashboard.injection.Activity;
import com.andela.art.sendfeedback.presentation.SendFeedbackActivity;

import dagger.Component;

/**
 * Send Feedback Component.
 */
@Activity
@Component(dependencies = {ApplicationComponent.class},
    modules = {
            ApplicationModule.class,
            SendFeedbackModule.class,
            ApiModule.class
    })
public interface SendFeedbackComponent {
    /**
     * Inject send feedback activity.
     * @param sendFeedbackActivity activity to inject.
     */
    void inject(SendFeedbackActivity sendFeedbackActivity);
}
