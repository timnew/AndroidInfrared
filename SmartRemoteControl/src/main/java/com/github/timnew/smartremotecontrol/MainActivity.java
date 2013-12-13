package com.github.timnew.smartremotecontrol;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.github.timnew.shared.viewpager.FragmentBuilder;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity
        extends SherlockFragmentActivity {

    @ViewById
    protected ViewPager pager;
    private PagerActionBarAdapter pagerActionBarAdapter;

    @AfterViews
    void afterViews() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        configureViewPager();
    }

    private void configureViewPager() {
        pagerActionBarAdapter = new PagerActionBarAdapter(getSupportFragmentManager(), getSupportActionBar(), pager);

        pagerActionBarAdapter.getFragmentBuilders().add(new FragmentBuilder() {
            @Override
            public Fragment buildFragment() {
                return ControlPanelFragment_.builder().layoutUrl("SamplePanel.html").build();
            }

            @Override
            public CharSequence getDisplayName() {
                return "Test";
            }
        });

        pagerActionBarAdapter.notifyDataSetChanged();
    }
}
