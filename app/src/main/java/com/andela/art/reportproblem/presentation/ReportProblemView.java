package com.andela.art.reportproblem.presentation;

import com.andela.art.models.ReportProblem;
import com.andela.art.root.View;

/**
 * ReportProblemView.
 */
public interface ReportProblemView extends View {

    /**
     * Report success submission of the report.
     *
     * @param reportProblem reportProblem model
     */
    void reportProblemSuccess(ReportProblem reportProblem);

    /**
     * Report unsuccessful submission of the report.
     *
     * @param e error
     */
    void reportProblemError(Throwable e);
}
