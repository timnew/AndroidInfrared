package com.github.timnew.smartremotecontrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.timnew.androidinfrared.IrdaManager;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;

import static com.github.timnew.androidinfrared.IrdaProtocols.NEC.buildNEC;

@EFragment(R.layout.tab_create_manager)
public class TabCreateManagerFragment extends Fragment {

    public static final String ON_SEQENCE = buildNEC(32, 0xFFE01F);
    public static final String OFF_SEQENCE = buildNEC(32, 0xFF609F);

    private IrdaManager irdaManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        irdaManager = IrdaManager.getIrdaManager(getActivity());
    }

    @Click(R.id.on_button)
    public void sendOn() {
        irdaManager.sendSequence(ON_SEQENCE);
    }


    @Click(R.id.off_button)
    public void sendOff() {
        irdaManager.sendSequence(OFF_SEQENCE);
    }
}

