
package com.github.timnew.shared.viewpager;

import android.app.Application;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formKey = "YOUR_FORM_KEY")
public class SmartRemoteControlApplication extends Application {

    @Override
    public void onCreate() {
        ACRA.init(this);
        super.onCreate();
    }

}
