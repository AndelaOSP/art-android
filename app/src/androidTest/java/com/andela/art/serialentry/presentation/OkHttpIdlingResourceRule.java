package com.andela.art.serialentry.presentation;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import okhttp3.OkHttpClient;

/**
 * Created by zack on 3/20/18.
 */

public class OkHttpIdlingResourceRule implements TestRule {
    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            OkHttpClient client = new OkHttpClient.Builder().build();
            @Override
            public void evaluate() throws Throwable {
                IdlingResource idlingResource = OkHttp3IdlingResource.create("oKhttp", client);
                IdlingRegistry.getInstance().register(idlingResource);
                base.evaluate();
                IdlingRegistry.getInstance().unregister(idlingResource);
            }
        };
    }
}
