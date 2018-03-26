package com.andela.art.serialentry.presentation;

import com.andela.art.api.ApiService;
import com.andela.art.serialentry.data.Asset;
import com.andela.art.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by zack on 3/26/18.
 */
public class SerialPresenterTest {
    private Asset asset;
    private SerialPresenter serialPresenter;

    @Mock
    ApiService apiService;

    @Mock
    SerialView serialView;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        serialPresenter = new SerialPresenter(apiService);
        serialPresenter.attachView(serialView);
        asset = new Asset();
    }

    @Test
    public void getAssetShouldReturnAsset(){
        when(apiService.getAsset(anyString())).thenReturn(Observable.just(asset));
        serialPresenter.getAsset(anyString());
        verify(serialView).sendIntent(asset);
    }


    @After
    public void tearDown() throws Exception {
    }

}