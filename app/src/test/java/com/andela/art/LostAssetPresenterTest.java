package com.andela.art;


import com.andela.art.lostasset.LostAssetModel;
import com.andela.art.lostasset.LostAssetPresenter;
import com.andela.art.lostasset.LostAssetView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

public class LostAssetPresenterTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    LostAssetView view;

    @Mock
    LostAssetModel model;

    LostAssetPresenter presenter;

    @Before
    public void setUp() {
        presenter = new LostAssetPresenter(view, model);
    }

    @Test
    public void loadsCohorts() {
        ArrayList<String> cohorts = new ArrayList();
        cohorts.add("Cohort 1");
        cohorts.add("Cohort 2");
        when(model.fetchCohorts()).thenReturn(cohorts);

        presenter.getCohorts();

        verify(view).showCohorts(cohorts);
    }

    @Test
    public void loadsAssets() {
        ArrayList<String> assets = new ArrayList();
        assets.add("Laptop");
        assets.add("Dongle");
        when(model.fetchAssets()).thenReturn(assets);

        presenter.getAssets();

        verify(view).showAssets(assets);
    }
}
