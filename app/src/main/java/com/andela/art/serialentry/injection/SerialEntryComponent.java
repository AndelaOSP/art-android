package com.andela.art.serialentry.injection;

import com.andela.art.api.ApiModule;
import com.andela.art.common.Activity;
import com.andela.art.common.ApplicationComponent;
import com.andela.art.serialentry.presentation.SerialEntryActivity;

import dagger.Component;

/**
 * Created by zack on 3/5/18.
 */
@Activity
@Component(dependencies = {ApplicationComponent.class}, modules = {ApiModule.class, SerialEntryModule.class})
public interface SerialEntryComponent {
    void inject(SerialEntryActivity serialEntryActivity);
}
