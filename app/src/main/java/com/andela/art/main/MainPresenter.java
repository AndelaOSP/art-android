package com.andela.art.main;

import javax.inject.Inject;

/**
 * Created by godwingitonga on 12/03/2018.
 */

public class MainPresenter {
    @Inject MainView view;

    @Inject
    public MainPresenter(MainView view) {
        this.view = view;
    }
}
