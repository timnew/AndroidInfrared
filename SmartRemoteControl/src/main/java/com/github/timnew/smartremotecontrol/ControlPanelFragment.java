package com.github.timnew.smartremotecontrol;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.ViewById;

import static android.widget.Toast.LENGTH_LONG;

@EFragment(R.layout.control_panel_fragment)
public class ControlPanelFragment extends Fragment {

    public static final String PANEL_PATH_TEMPLATE = "file:///android_asset/panels/%s/index.html";
    public static final String PANELS_PATH = "panels";
    public static final String IR_PANEL = "IR Panel";

    @FragmentArg
    protected String layoutUrl;

    @ViewById(R.id.panel)
    protected WebView panel;

    @Bean
    protected InfraredEmitter emitter;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetJavaScriptEnabled")
    @AfterViews
    protected void afterView() {
        WebSettings settings = panel.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);

        panel.addJavascriptInterface(emitter, "ir");
        panel.setWebViewClient(new WebViewClient() {
        });

        panel.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                String messageText = String.format("[%s](#%d):%s", consoleMessage.sourceId(), consoleMessage.lineNumber(), consoleMessage.message());
                Toast.makeText(getActivity(), consoleMessage.message(), LENGTH_LONG).show();

                switch (consoleMessage.messageLevel()) {
                    case DEBUG:
                        Log.d(IR_PANEL, messageText);
                        break;
                    case ERROR:
                        Log.e(IR_PANEL, messageText);
                        break;
                    case LOG:
                        Log.v(IR_PANEL, messageText);
                        break;
                    case TIP:
                        Log.i(IR_PANEL, messageText);
                        break;
                    case WARNING:
                        Log.w(IR_PANEL, messageText);
                        break;
                }

                return true;
            }
        });

        loadPanel();

    }

    protected void loadPanel() {
        panel.loadUrl(layoutUrl);
    }
}