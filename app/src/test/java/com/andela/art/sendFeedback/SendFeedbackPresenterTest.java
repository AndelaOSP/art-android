package com.andela.art.sendFeedback;

import com.andela.art.api.ApiService;
import com.andela.art.models.SendFeedback;
import com.andela.art.sendfeedback.presentation.SendFeedbackPresenter;
import com.andela.art.sendfeedback.presentation.SendFeedbackView;
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
 * Test send feedback presenter.
 */
public class SendFeedbackPresenterTest {
    private SendFeedback sendFeedback;
    @Mock
    ApiService apiService;

    @Mock
    SendFeedbackView sendFeedbackView;

    @Mock
    Throwable error;

    SendFeedbackPresenter sendFeedbackPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    /**
     * Initialize test class and mocks.
     * @throws Exception - exception
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sendFeedbackPresenter = new SendFeedbackPresenter(apiService);
        sendFeedbackPresenter.attachView(sendFeedbackView);
        sendFeedback = new SendFeedback();
    }

    /**
     * Test successful sending of feedback.
     */
    @Test
    public void sendFeedbackSuccess() {
        when(apiService.sendFeedback(anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(sendFeedback));
        sendFeedbackPresenter.sendFeedback(anyString(), anyString(), anyString());
        verify(sendFeedbackView).sendFeedbackSuccess(sendFeedback);
    }

    /**
     * Test sending of feedback failure.
     *
     * @throws Throwable - exception if error occurs.
     */
    @Test
    public void sendFeedbackFailure() throws Throwable {
        when(apiService.sendFeedback(anyString(), anyString(), anyString()))
                .thenReturn(Observable.error(error));
        sendFeedbackPresenter.sendFeedback(anyString(), anyString(), anyString());
        verify(sendFeedbackView).sendFeedbackError(error);
    }
}
