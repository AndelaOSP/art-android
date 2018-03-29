package com.andela.art.serialentry.presentation;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import okhttp3.mockwebserver.MockWebServer;

/**
 * Created by zack on 3/28/18.
 */

public class MockWebServerRule implements TestRule {
    public final MockWebServer server = new MockWebServer();

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                server.start(3000);
                base.evaluate();
                server.shutdown();
            }
        };
    }
}
