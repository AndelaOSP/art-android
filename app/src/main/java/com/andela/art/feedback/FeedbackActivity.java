package com.andela.art.feedback;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.databinding.ActivityFeedbackBinding;
import com.andela.art.models.Feedback;
import com.andela.art.feedback.service.PostFeedback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 * This activity handles the submitting of feedback from a user.
 */
public class FeedbackActivity extends AppCompatActivity {

    ActivityFeedbackBinding binding;
    GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_feedback);

        setSupportActionBar(binding.feedbackToolbar);

        account = GoogleSignIn
                .getLastSignedInAccount(this);
    }

    /**
     * Handle sending of the entered feedback to the backend.
     *
     * @param view The view that calls this method which is an ImageView
     */
    public void sendFeedback(View view) {
        String feedback = binding.etFeedbackText.getText().toString().trim();

        if (!feedback.isEmpty()) {
            try {
                new PostFeedback().sendFeedbackToAPI(
                        new Feedback(account.getEmail(), feedback, "feedback"))
                        .subscribe(new Consumer<Feedback>() {
                    @Override
                    public void accept(Feedback feedback) {
                        Toast.makeText(FeedbackActivity.this, "Sent",
                                Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) {
                        if (e instanceof HttpException) {
                            toggleSnackBar("An error has occurred. Details: "
                                    + ((HttpException) e).message());
                        } else if (e instanceof SocketTimeoutException) {
                            toggleSnackBar("The request has timed out. Please try again.");
                        } else if (e instanceof IOException) {
                            toggleSnackBar("A network error has occurred. Please try again.");
                        } else {
                            toggleSnackBar("An error has occurred. Please try again.");
                        }
                    }
                });
            } catch (Exception e) {
                Log.e("Error occurred", e.getMessage());
            }
        } else {
            binding.etFeedbackText.setError("Enter your feedback message");
        }

    }

    /**
     * SNACKBAR for error messages.
     *
     * @param msg the message to be displayed
     */
    void toggleSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.etFeedbackCoordinatorLayout,
                msg, Snackbar.LENGTH_LONG);
        snackbar.show();
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
