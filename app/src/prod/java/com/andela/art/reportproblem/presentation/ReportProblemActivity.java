package com.andela.art.reportproblem.presentation;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.databinding.ActivityReportProblemBinding;
import com.andela.art.models.ReportProblem;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.root.ArtApplication;
import com.andela.art.reportproblem.injection.DaggerReportProblemComponent;
import com.andela.art.reportproblem.injection.ReportProblemModule;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

/**
 * Report issues about the application.
 */
public class ReportProblemActivity extends AppCompatActivity implements ReportProblemView {
    @Inject
    ReportProblemPresenter reportProblemPresenter;

    ActivityReportProblemBinding reportBinding;
    private FirebaseAuth mAuth;
    ApplicationComponent applicationComponent;

    /**
     * Launch the activity.
     *
     * @param savedInstanceState - The save instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reportBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_report_problem
        );
        setSupportActionBar(reportBinding.reportToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        applicationComponent = ((ArtApplication) getApplication()).applicationComponent();
        initializeReportProblemComponent();
        reportProblemPresenter.attachView(this);

        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Initialize Report Problem Component.
     */
    public void initializeReportProblemComponent() {
        DaggerReportProblemComponent.builder()
                .applicationComponent(applicationComponent)
                .applicationModule(new ApplicationModule(getApplication()))
                .reportProblemModule(new ReportProblemModule())
                .build()
                .inject(this);
    }

    /**
     * Cancel reporting a problem and move back to the previous activity.
     *
     * @param view The view that calls this method which is an ImageView
     */
    public void cancelReportSending(View view) {
        finish();
    }

    /**
     * Submit the report.
     *
     * @param view The view that calls this method is an ImageView
     */
    public void sendReport(View view) {
        String email = mAuth.getCurrentUser().getEmail();
        String message = reportBinding.reportProblemText.getText().toString();
        String reportType = "bug";

        if (message.isEmpty()) {
            String toastMessage = "Cannot submit an empty report";
            displayToast(toastMessage);
            return;
        }

        reportProblemPresenter.reportProblem(email, message, reportType);

        finish();
    }

    /**
     * Success on call of an endpoint.
     * @param reportProblem reportProblem model
     */
    public void reportProblemSuccess(ReportProblem reportProblem) {
        String toastMessage = "Report submitted successfully";
        displayToast(toastMessage);

        reportProblemPresenter.dispose();
    }

    /**
     * Show error resulted from call of the endpoint.
     *
     * @param e throwable exception
     */
    public void reportProblemError(Throwable e) {
        String toastMessage = "Report not submitted. Please try again";
        displayToast(toastMessage);
    }

    /**
     * Display toast feedback.
     *
     * @param message The toast message
     */
    public void displayToast(String message) {
        Toast.makeText(
                getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }
}
