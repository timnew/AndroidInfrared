package com.github.timnew.androidinfrared;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity
        extends SherlockFragmentActivity
        implements OnPageChangeListener, TabListener {

    @ViewById
    ViewPager pager;

    @AfterViews
    void afterViews() {
        configureViewPager();
        configureActionBar();
    }

    private void configureViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(viewPagerAdapter);
        pager.setOnPageChangeListener(this);
    }

    public void onPageSelected(int position) {
        Tab tab = getSupportActionBar().getTabAt(position);
        getSupportActionBar().selectTab(tab);
    }

    private void configureActionBar() {
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (MainTabs tabInfo : MainTabs.values()) {
            Tab tab = getSupportActionBar().newTab();
            tab.setText(tabInfo.getName());
            tab.setTabListener(this);
            getSupportActionBar().addTab(tab);
        }
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        int position = tab.getPosition();
        pager.setCurrentItem(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater();
        return true;
    }

    @Override
    public void onPageScrollStateChanged(int position) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    public static class ViewPagerAdapter
            extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public int getCount() {
            return MainTabs.values().length;
        }

        public Fragment getItem(int position) {
            MainTabs tab = MainTabs.values()[position];

            return tab.getFragment();
        }

    }

    public static enum MainTabs {
        CreateManager {
            @Override
            public Fragment getFragment() {
                return new TabCreateManagerFragment_();
            }

            @Override
            public int getName() {
                return R.string.tab_create_manager;
            }
        },
        Info {
            @Override
            public Fragment getFragment() {
                return new TabInfoFragment_();
            }

            @Override
            public int getName() {
                return R.string.tab_info;
            }
        };

        public abstract Fragment getFragment();

        public abstract int getName();
    }
}
