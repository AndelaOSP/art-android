package com.andela.art.serialentry.injection;

import com.andela.art.common.ApplicationComponent;
import com.andela.art.common.DataModule;
import com.andela.art.serialentry.domain.GetAssetUseCase;
import com.andela.art.serialentry.presentation.SerialEntryActivity;

import dagger.Component;

/**
 * Created by zack on 3/5/18.
 */
@Activity @Component(dependencies = {ApplicationComponent.class}, modules = {SerialEntryModule.class})
public interface SerialEntryComponent {
    void inject(SerialEntryActivity serialEntryActivity);
}
