package com.bhargo.user.utils;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.bhargo.user.R;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(
        mailTo = "sanjay.esther@gmail.com",
        customReportContent = {ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.issue_report
)
public class Application extends MultiDexApplication {
    public Application() {
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        ACRA.init(this);
        ACRA.getErrorReporter().putCustomData("SMART_SDK","1");
        ACRA.getErrorReporter().putCustomData("SMART_API", "1.0");
        ACRA.getErrorReporter().putCustomData("SMART_URL","Exception");
        super.onCreate();

    }
}