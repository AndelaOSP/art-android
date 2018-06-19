package com.andela.art.reportIncident;

import com.andela.art.api.ApiService;
import com.andela.art.incidentreport.presentation.IncidentReportPresenter;
import com.andela.art.incidentreport.presentation.IncidentReportView;
import com.andela.art.models.IncidentModel;
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
 * Test Incident Report presenter.
 */
public class IncidentReportPresenterTest {
    private IncidentModel incidentModel;

    @Mock
    ApiService apiService;

    @Mock
    IncidentReportView incidentReportView;

    IncidentReportPresenter incidentReportPresenter;

    @Rule
    public final RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    /**
     * Initialize test class and mocks.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        incidentReportPresenter = new IncidentReportPresenter(apiService);
        incidentReportPresenter.attachView(incidentReportView);
        incidentModel = new IncidentModel();
    }

    /**
     * Test successful submission of the incidence.
     */
    @Test
    public void reportIncidenceSuccess() {
        when(apiService.reportIncident(incidentModel)).thenReturn(Observable.just(incidentModel));

        incidentReportPresenter.reportIncident(incidentModel);
        verify(incidentReportView).showSuccess();
    }
}
