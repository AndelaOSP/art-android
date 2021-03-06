package com.andela.art.securitydashboard.presentation;

import com.andela.art.api.ApiService;
import com.andela.art.api.UserAssetResponse;
import com.andela.art.util.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by zack on 3/26/18.
 */
public class SerialPresenterTest {
    private UserAssetResponse asset;
    private SerialPresenter serialPresenter;

    @Mock
    ApiService apiService;

    @Mock
    SerialView serialView;

    @Mock
    Throwable error;

    @Rule
    public final RxSchedulersOverrideRule overrideSchedulersRule = new RxSchedulersOverrideRule();

    /**
     * Test setup method.
     * @throws Exception if an exception is raised.
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        serialPresenter = new SerialPresenter(apiService);
        serialPresenter.attachView(serialView);
        asset = new UserAssetResponse();
    }

    /**
     * Return asset when serial presenter get asset is called.
     */
    @Test
    public void getAssetShouldReturnAsset() {
        when(apiService.getAsset(anyString())).thenReturn(Observable.just(asset));
        serialPresenter.getAsset(anyString());
        verify(serialView).sendIntent(asset);
    }

    /**
     * Return error when serial presenter get asset is called.
     * @throws Throwable if error occurs
     */
    @Test
    public void getAssetErrorShouldDisplayError() throws Throwable {
        when(apiService.getAsset(anyString())).thenReturn(Observable.error(error));
        serialPresenter.getAsset(anyString());
        verify(serialView).displayErrorMessage(error);
    }
}
