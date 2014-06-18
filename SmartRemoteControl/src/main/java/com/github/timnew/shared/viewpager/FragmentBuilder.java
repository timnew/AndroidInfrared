package com.github.timnew.shared.viewpager;

import android.support.v4.app.Fragment;

public interface FragmentBuilder {

    Fragment buildFragment();

    CharSequence getDisplayName();
}
