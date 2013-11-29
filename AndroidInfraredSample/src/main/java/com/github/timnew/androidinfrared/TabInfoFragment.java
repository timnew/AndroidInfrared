package com.github.timnew.androidinfrared;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.os.Build;
import com.googlecode.androidannotations.annotations.EFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@EFragment(R.layout.tab_info)
public class TabInfoFragment extends Fragment {
}

