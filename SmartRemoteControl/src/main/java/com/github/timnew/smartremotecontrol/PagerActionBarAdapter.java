package com.github.timnew.smartremotecontrol;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.github.timnew.shared.viewpager.FragmentBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PagerActionBarAdapter
        extends FragmentPagerAdapter
        implements ViewPager.OnPageChangeListener, ActionBar.TabListener {

    private final ActionBar actionBar;
    private final ViewPager pager;
    private final List<FragmentBuilder> fragmentBuilders;
    private final Context context;

    public PagerActionBarAdapter(Context applicationContext, FragmentManager fragmentManager, ActionBar actionBar, ViewPager pager) {
        super(fragmentManager);

        this.context = applicationContext;

        this.actionBar = actionBar;
        this.pager = pager;
        this.fragmentBuilders = new ArrayList<FragmentBuilder>();

        initPager();
        initActionBar();
    }

    public List<FragmentBuilder> getFragmentBuilders() {
        return fragmentBuilders;
    }

    private void initPager() {
        pager.setAdapter(this);
        pager.setOnPageChangeListener(this);
    }

    private void initActionBar() {
        this.actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        rescanPanels();
    }

    public void rescanPanels() {
        this.fragmentBuilders.clear();

        Resources res = context.getResources();
        AssetManager am = res.getAssets();

        try {
            String[] panelDirs = am.list("panels");

            for (String panel : panelDirs) {
                final String name = panel;
                final String panelFile = String.format("file:///android_asset/panels/%s/index.html", panel);

                fragmentBuilders.add(new FragmentBuilder() {
                    @Override
                    public Fragment buildFragment() {
                        return ControlPanelFragment_.builder().layoutUrl(panelFile).build();
                    }

                    @Override
                    public CharSequence getDisplayName() {
                        return name;
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateActionBar() {
        this.actionBar.removeAllTabs();

        for (FragmentBuilder builder : this.fragmentBuilders) {
            ActionBar.Tab tab = this.actionBar.newTab();
            tab.setText(builder.getDisplayName());
            tab.setTabListener(this);
            this.actionBar.addTab(tab);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        updateActionBar();
    }

    public int getCount() {
        return fragmentBuilders.size();
    }

    public Fragment getItem(int position) {
        return fragmentBuilders.get(position).buildFragment();
    }

    @Override
    public void onPageSelected(int position) {
        ActionBar.Tab tab = actionBar.getTabAt(position);
        actionBar.selectTab(tab);
    }

    @Override
    public void onPageScrollStateChanged(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        int position = tab.getPosition();
        pager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
}