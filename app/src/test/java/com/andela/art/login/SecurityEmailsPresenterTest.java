package com.andela.art.login;

import com.andela.art.api.ApiService;
import com.andela.art.api.EmailsResponse;
import com.andela.art.api.TokenResponse;
import com.andela.art.root.SharedPrefsWrapper;
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
 * Created by zack on 4/17/18.
 */
public class SecurityEmailsPresenterTest {
    @Mock
    ApiService apiService;

    @Mock
    SecurityEmailsView securityEmailsView;

    @Mock
    SharedPrefsWrapper sharedPrefsWrapper;

    SecurityEmailsPresenter securityEmailsPresenter;

    EmailsResponse emailsResponse;
    TokenResponse tokenResponse;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    /**
     * Intiailize test class and mocks.
     * @throws Exception - exception
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        securityEmailsPresenter = new SecurityEmailsPresenter(sharedPrefsWrapper, apiService);
        securityEmailsPresenter.attachView(securityEmailsView);
        emailsResponse = new EmailsResponse();
        tokenResponse = new TokenResponse();


    }

    /**
     *
     * @throws Exception - exception
     */
    @Test
    public void testOauthTokenisRetrieved() throws Exception {
        when(apiService.fetchOauthToken(anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(tokenResponse));
        when(apiService.getEmails()).thenReturn(Observable.just(emailsResponse));
        securityEmailsPresenter.retrieveOauthToken();
        verify(securityEmailsView).populateEmailList(emailsResponse.getEmails());

    }
    /**
     *
     * @throws Exception - exception
     */
    @Test
    public void testSecurityEmailsAreAddedtoList() throws Exception {
        when(apiService.getEmails()).thenReturn(Observable.just(emailsResponse));
        securityEmailsPresenter.fetchSecurityUserEmails();
        verify(securityEmailsView).populateEmailList(emailsResponse.getEmails());
    }
}
