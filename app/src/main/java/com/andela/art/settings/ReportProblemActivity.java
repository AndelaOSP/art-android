package com.andela.art.settings;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

    }

    /**
     * Cancel reporting a problem and move back to the previous activity.
     *
     * @param view The view that calls this method which is an ImageView
     */
    public void cancelReportSending(View view) {
        finish();
    }

}
