package com.andela.art.settings.presentation;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.databinding.ActivityReportProblemBinding;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ArtApplication;
import com.andela.art.settings.injection.DaggerReportProblemComponent;
import com.andela.art.settings.injection.ReportProblemModule;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

/**
 * Report issues about the application.
 */
public class ReportProblemActivity extends AppCompatActivity {
    @Inject
    ReportProblemPresenter reportProblemPresenter;

    ActivityReportProblemBinding reportBinding;
    private FirebaseAuth mAuth;

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

        ApplicationComponent applicationComponent = ((ArtApplication) getApplication())
                .applicationComponent();

        DaggerReportProblemComponent.builder()
                .applicationComponent(applicationComponent)
                .reportProblemModule(new ReportProblemModule())
                .build()
                .inject(this);

        mAuth = FirebaseAuth.getInstance();
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
     * @param view The view that calls this method whis is an ImageView
     */
    public void sendReport(View view) {
        String email = mAuth.getCurrentUser().getEmail();
        String message = reportBinding.reportProblemText.getText().toString();
        String reportType = "bug";

        if (message.isEmpty()) {
            Toast.makeText(this, "Cannot submit an empty report", Toast.LENGTH_LONG).show();
            return;
        }

        reportProblemPresenter.reportProblem(email, message, reportType);

        finish();
    }
}
