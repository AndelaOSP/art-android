package com.andela.art.incidentreport.presentation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.andela.art.R;
import com.andela.art.databinding.ActivityIncidentBinding;

/**
 * Incident report activity.
 */
public class IncidentReportActivity extends AppCompatActivity implements IncidentReportView {
    ActivityIncidentBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_incident);

        setSupportActionBar(binding.incidentToolbar);
        binding.incidentToolbar.setNavigationIcon(getResources()
                .getDrawable(R.drawable.ic_back_left_arrow));

        binding.incidentSubmit.setOnClickListener(v -> {
            binding.incidentSubmit.setBackground(getResources()
                    .getDrawable(R.drawable.pressed_incident_button));
        });

    }


}
