package com.andela.art.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.andela.art.R;

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
        setContentView(R.layout.activity_report_problem);

        Toolbar toolbar = findViewById(R.id.report_toolbar);
        setSupportActionBar(toolbar);

        // Remove the default toolbar title.
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }
}
