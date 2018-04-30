package com.andela.art.reportProblem;

import com.andela.art.api.ApiService;
import com.andela.art.models.ReportProblem;
import com.andela.art.reportproblem.presentation.ReportProblemPresenter;
import com.andela.art.reportproblem.presentation.ReportProblemView;
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
 * Test report problem presenter.
 */
public class ReportProblemPresenterTest {
    private ReportProblem reportProblem;

    @Mock
    ApiService apiService;

    @Mock
    ReportProblemView reportProblemView;

    @Mock
    Throwable error;

    ReportProblemPresenter reportProblemPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    /**
     * Initialize test class and mocks.
     * @throws Exception - exception
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        reportProblemPresenter = new ReportProblemPresenter(apiService);
        reportProblemPresenter.attachView(reportProblemView);
        reportProblem = new ReportProblem();
    }

    /**
     * Test successful submission of the problem.
     */
    @Test
    public void reportProblemSuccess() {
        when(apiService.reportProblem(anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(reportProblem));

        reportProblemPresenter.reportProblem(anyString(), anyString(), anyString());
        verify(reportProblemView).reportProblemSuccess(reportProblem);
        reportProblemPresenter.dispose();
    }

    /**
     * Test submission of report problem failure.
     *
     * @throws Throwable - exception if error occurs.
     */
    @Test
    public void reportProblemFailure() throws Throwable {
        when(apiService.reportProblem(anyString(), anyString(), anyString()))
                .thenReturn(Observable.error(error));
        reportProblemPresenter.reportProblem(anyString(), anyString(), anyString());
        verify(reportProblemView).reportProblemError(error);
    }

}
