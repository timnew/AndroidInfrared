package com.github.timnew.smartremotecontrol;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.ViewById;

import java.net.URI;

@EFragment(R.layout.control_panel_fragment)
public class ControlPanelFragment extends Fragment {

    public static final String PANEL = "Panel";
    public static final String ASSET_PATH = "file:///android_asset/";

    @FragmentArg
    protected String layoutUrl;

    @ViewById(R.id.panel)
    protected WebView panel;

    @Bean
    protected InfraredEmitter emitter;

    @Bean
    protected AssetsPathProvider pathProvider;

    @SuppressLint("SetJavaScriptEnabled")
    @AfterViews
    protected void afterView() {
        panel.getSettings().setJavaScriptEnabled(true);
        panel.addJavascriptInterface(emitter, "ir");
        panel.addJavascriptInterface(pathProvider, "path");
        panel.setWebViewClient(new WebViewClient() {
        });

        panel.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                String messageText = String.format("[%s](#%d):%s", consoleMessage.sourceId(), consoleMessage.lineNumber(), consoleMessage.message());
                Toast.makeText(getActivity(), consoleMessage.message(), Toast.LENGTH_LONG).show();

                switch (consoleMessage.messageLevel()) {
                    case DEBUG:
                        Log.d(PANEL, messageText);
                        break;
                    case ERROR:
                        Log.e(PANEL, messageText);
                        break;
                    case LOG:
                        Log.v(PANEL, messageText);
                        break;
                    case TIP:
                        Log.i(PANEL, messageText);
                        break;
                    case WARNING:
                        Log.w(PANEL, messageText);
                        break;
                }

                return true;
            }
        });

        loadPanel();
    }

    protected void loadPanel() {
        String fullUrl;

        if (Uri.parse(layoutUrl).isRelative())
            fullUrl = ASSET_PATH + layoutUrl;
        else
            fullUrl = layoutUrl;

        panel.loadUrl(fullUrl);
    }
}