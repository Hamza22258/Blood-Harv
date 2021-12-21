package com.hamza.blood_harv;

import android.app.Application;

import com.onesignal.OneSignal;

public class MainApplication extends Application {
    private static final String ONESIGNAL_APP_ID = "7fc1ff2a-043a-4e94-bff7-8853d7801894";

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
