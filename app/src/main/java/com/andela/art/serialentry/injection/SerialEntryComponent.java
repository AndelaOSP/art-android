package com.andela.art.serialentry.injection;

import com.andela.art.api.ApiModule;
import com.andela.art.common.ApplicationComponent;
import com.andela.art.serialentry.presentation.SerialEntryActivity;

import dagger.Component;

/**
 * Created by zack on 3/5/18.
 */
@Activity
@Component(dependencies = {ApplicationComponent.class},
        modules = {ApiModule.class, SerialEntryModule.class})
public interface SerialEntryComponent {
    /**
     * Inject serial presenter to serial entry activity.
     * @param serialEntryActivity activity where serial presenter will be injected
     */
    void inject(SerialEntryActivity serialEntryActivity);
}
