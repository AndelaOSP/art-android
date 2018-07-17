package com.andela.art.userdashboard.presentation;


import com.andela.art.api.ApiService;
import com.andela.art.api.UserAssetResponse;
import com.andela.art.util.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by zack on 5/14/18.
 */
public class AssetsPresenterTest {

    AssetsPresenter assetsPresenter;
    @Mock
    ApiService apiService;

    @Mock
    SliderView sliderView;

    @Rule
    public  final RxSchedulersOverrideRule overrideRule = new RxSchedulersOverrideRule();

    /**
     * Test set up method.
     * @throws Exception - exception
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        assetsPresenter = new AssetsPresenter(apiService);
        assetsPresenter.attachView(sliderView);
    }

    /**
     * Test that assets were fetched and passed to slider view.
     */
    @Test
    public void testAssetsListFetched() {
        UserAssetResponse assets = new UserAssetResponse();
        when(apiService.getAssets()).thenReturn(Observable.just(assets));
        assetsPresenter.getAssets();
        verify(sliderView).onGetAssets(assets);
    }
}
