package com.andela.art.settings;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.databinding.ActivityReportProblemBinding;

/**
 * Report issues about the application.
 */
public class ReportProblemActivity extends AppCompatActivity {

    /**
     * Launch the activity.
     *
     * @param savedInstanceState - The save instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityReportProblemBinding reportBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_report_problem
        );
        setSupportActionBar(reportBinding.reportToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        Toast.makeText(this, "Send Report", Toast.LENGTH_SHORT).show();
    }
}
