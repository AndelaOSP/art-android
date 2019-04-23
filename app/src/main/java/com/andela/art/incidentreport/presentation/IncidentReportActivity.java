package com.andela.art.incidentreport.presentation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
public class IncidentReportActivity extends AppCompatActivity implements IncidentReportView,
        AdapterView.OnItemSelectedListener {
    ActivityIncidentBinding binding;
    ApplicationComponent applicationComponent;
    Bundle bundle;
    Asset asset;
    String incidentType;

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
        setArtEditTextListener();

        ArrayAdapter adapter = ArrayAdapter.createFromResource(IncidentReportActivity.this,
                R.array.incident_options, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.type.setAdapter(adapter);
        binding.type.setOnItemSelectedListener(this);

        binding.incidentSubmit.setOnClickListener(v -> {
            binding.incidentSubmit.setBackground(getResources()
                    .getDrawable(R.drawable.pressed_incident_button));
            submitReport();
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
        model.setAsset(String.valueOf(asset.getId()));
        model.setIncidentType(incidentType);
        model.setIncidentDescription(binding.description.getText().toString().trim());
        model.setIncidentLocation(binding.location.getText().toString().trim());
        model.setInjuriesSustained(binding.injuries.getText().toString().trim());
        model.setLossOfProperty(binding.property.getText().toString().trim());
        model.setWitnesses(binding.witness.getText().toString().trim());
        model.setPoliceAbstractObtained(binding.abstractReport.getText().toString().trim());

        presenter.reportIncident(model);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public void showSuccess() {
        toast =  Toast.makeText(this.getApplicationContext(),
               "Incident reported successfully", Toast.LENGTH_LONG);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 4000);
    }

    @Override
    public void displayError() {
        Toast.makeText(getApplicationContext(),
                "Incident reporting failed, please try again.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        incidentType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        incidentType = "Damaged";

    }

    /**
     * EditText input listener.
     */
    public void setArtEditTextListener() {
        binding.location.addTextChangedListener(new ArtTextWatcher(binding.location));
        binding.description.addTextChangedListener(new ArtTextWatcher(binding.description));
        binding.injuries.addTextChangedListener(new ArtTextWatcher(binding.injuries));
        binding.property.addTextChangedListener(new ArtTextWatcher(binding.property));
        binding.witness.addTextChangedListener(new ArtTextWatcher(binding.witness));
        binding.abstractReport.addTextChangedListener(new ArtTextWatcher(binding.abstractReport));
    }


    /**
     * Submit report details.
     */
    public void submitReport() {
        int[] ids = new int[]
            {
                R.id.location,
                R.id.description,
                R.id.injuries,
                R.id.property,
                R.id.witness,
                R.id.abstract_report
            };

        if (validateEditText(ids)) {
            binding.incidentSubmit.setBackground(getResources()
                    .getDrawable(R.drawable.incident_button));
        } else {
            enterReportDetails();
        }
    }

    /**
     * Validate for empty EditText fields.
     *
     * @param ids - Array ids
     * @return isEmpty - Boolean
     */
    public boolean validateEditText(int... ids) {
        boolean isEmpty = false;

        for (int id : ids) {
            ArtEditText input = findViewById(id);

            if (TextUtils.isEmpty(input.getText().toString())) {
                input.setError("");
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    /**
     * Private TextWatcher class.
     */
    private class ArtTextWatcher implements TextWatcher {

        private final View view;
        /**
         * Constructor for ArtTextWatcher class.
         * @param view - View
         */
        ArtTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)  {
            // runs during the text changing.
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)  {
            //  runs the instant before the text is changed.
        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.location:
                    binding.location.setError(null);
                    break;
                case R.id.description:
                    binding.description.setError(null);
                    break;
                case R.id.injuries:
                    binding.injuries.setError(null);
                    break;
                case R.id.property:
                    binding.property.setError(null);
                    break;
                case R.id.witness:
                    binding.witness.setError(null);
                    break;
                case R.id.abstract_report:
                    binding.abstractReport.setError(null);
                    break;
                default:
                    break;
            }
        }
    }
}
