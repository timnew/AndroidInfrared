package com.github.timnew.smartremotecontrol;

import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
public class MainActivity
        extends SherlockFragmentActivity {

    @ViewById
    protected ViewPager pager;
    private PagerActionBarAdapter pagerActionBarAdapter;

    @Bean
    protected InfraredEmitter emitter;

    @AfterViews
    void afterViews() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        configureViewPager();
    }

    private void configureViewPager() {
        pagerActionBarAdapter = new PagerActionBarAdapter(this, pager);

//        pagerActionBarAdapter.registerRemotePanel("debug", "http://192.168.1.6:4000/panels/tv%20box/index.html#box");

        pagerActionBarAdapter.notifyDataSetChanged();

        checkIrSupport();
    }

    private void checkIrSupport() {
        if (emitter.hasIrEmitter())
            return;

        finish();

        Intent intent = new Intent(this, DialogHostActivity.class);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater supportMenuInflater = getSupportMenuInflater();

        supportMenuInflater.inflate(R.menu.main_activity, menu);

        return true;
    }

    @OptionsItem(R.id.menu_add_remote_panel)
    protected void showAddRemotePanel() {

    }
}
