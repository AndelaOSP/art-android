package com.andela.art.injection.component;

import com.andela.art.injection.modules.CheckinModule;

import dagger.Component;

@Component(modules = CheckinModule.class, dependencies = ApplicationComponent.class)
public interface CheckinComponent {
}
