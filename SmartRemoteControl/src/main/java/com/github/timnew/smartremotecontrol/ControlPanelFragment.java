package com.github.timnew.smartremotecontrol;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.webkit.WebView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.control_panel_fragment)
public class ControlPanelFragment extends Fragment {

    @FragmentArg
    protected String layoutUrl;

    @ViewById(R.id.panel)
    protected WebView panel;

    @Bean
    protected InfraredEmitter emitter;

    @SuppressLint("SetJavaScriptEnabled")
    @AfterViews
    protected void afterView() {
        panel.getSettings().setJavaScriptEnabled(true);
        panel.addJavascriptInterface(emitter, "IR");

        loadPanel();
    }

    protected void loadPanel() {
        panel.loadUrl("file:///android_asset/" + layoutUrl);
    }
}