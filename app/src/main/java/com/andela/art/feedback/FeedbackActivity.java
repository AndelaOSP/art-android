package com.andela.art.feedback;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.databinding.ActivityFeedbackBinding;

/**
 * This activity handles the submitting of feedback from a user.
 */
public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFeedbackBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_feedback);

        setSupportActionBar(binding.feedbackToolbar);
    }

    /**
     * Handle sending of the entered feedback to the backend.
     *
     * @param view The view that calls this method which is an ImageView
     */
    public void sendFeedback(View view) {
        Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
    }

    /**
     * Cancel and close the activity by going back.
     *
     * @param view The view that calls this method which is an ImageView
     */
    public void cancelFeedback(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
