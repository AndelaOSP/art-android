package com.andela.art.injection.component;

import com.andela.art.checkin.CheckInActivity;
import com.andela.art.injection.modules.CheckinModule;
import com.andela.art.injection.scope.PerActivity;
import com.andela.art.root.ApplicationComponent;

import dagger.Component;

@Component(modules = CheckinModule.class, dependencies = ApplicationComponent.class)
@PerActivity
public interface CheckinComponent {
    void inject(CheckInActivity checkInActivity);
}
