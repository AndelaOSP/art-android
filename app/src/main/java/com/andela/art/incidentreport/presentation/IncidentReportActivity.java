package com.andela.art.incidentreport.presentation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.databinding.ActivityIncidentBinding;
import com.andela.art.incidentreport.injection.DaggerReportIncidentComponent;
import com.andela.art.incidentreport.injection.IncidentReportModule;
import com.andela.art.models.Asset;
import com.andela.art.models.IncidentModel;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.root.ArtApplication;

import javax.inject.Inject;

/**
 * Incident report activity.
 */
public class IncidentReportActivity extends AppCompatActivity implements IncidentReportView {
    ActivityIncidentBinding binding;
    ApplicationComponent applicationComponent;
    Bundle bundle;
    Asset asset;

    @Inject
    IncidentReportPresenter  presenter;
    @VisibleForTesting
    public Toast toast;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_incident);
        applicationComponent = ((ArtApplication) getApplication())
                .applicationComponent();
        initializeIncidentReport();
        setSupportActionBar(binding.incidentToolbar);
        binding.incidentToolbar.setNavigationIcon(getResources()
                .getDrawable(R.drawable.ic_back_left_arrow));

        binding.incidentSubmit.setOnClickListener(v -> {
            binding.incidentSubmit.setBackground(getResources()
                    .getDrawable(R.drawable.pressed_incident_button));
            enterReportDetails();
        });

        binding.incidentToolbar.setNavigationOnClickListener(view -> onBackPressed());

        presenter.attachView(this);
        bundle = getIntent().getExtras();
        asset = (Asset) bundle.getSerializable("asset");
    }

    /**
     * Initialize the component.
     */
    private void initializeIncidentReport() {
        DaggerReportIncidentComponent.builder()
                .applicationComponent(applicationComponent)
                .applicationModule(new ApplicationModule(getApplication()))
                .incidentReportModule(new IncidentReportModule())
                .build()
                .inject(this);
    }

    /**
    * Enter report details.
     */
    private void enterReportDetails() {
        IncidentModel model = new IncidentModel();
        model.setIncidentDescription(binding.description.getText().toString().trim());
        model.setIncidentLocation(binding.location.getText().toString().trim());
        model.setInjuriesSustained(binding.injuries.getText().toString().trim());
        model.setLossOfProperty(binding.property.getText().toString().trim());
        model.setWitnesses(binding.witness.getText().toString().trim());
        model.setPoliceAbstractObtained(binding.abstractReport.getText().toString().trim());

        presenter.reportIncident(model);
    }


    @Override
    public void showSuccess() {
       toast =  Toast.makeText(getApplicationContext(),
               "Incident reported successfully", Toast.LENGTH_LONG);
       toast.show();
    }

}
