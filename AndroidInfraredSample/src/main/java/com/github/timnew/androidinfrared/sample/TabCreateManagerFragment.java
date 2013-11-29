package com.github.timnew.androidinfrared.sample;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.github.timnew.androidinfrared.IrdaManager;
import com.github.timnew.androidinfrared.NecIrSequence;
import com.github.timnew.androidinfrared.R;
import com.github.timnew.androidinfrared.RawIrSequence;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@EFragment(R.layout.tab_create_manager)
public class TabCreateManagerFragment extends Fragment {

    public static final NecIrSequence ON_SEQENCE = new NecIrSequence(32, 0xFFE01F);
    public static final NecIrSequence OFF_SEQENCE = new NecIrSequence(32, 0xFF609F);

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

    @Click(R.id.parse_button)
    public void sendRaw(){
        irdaManager.sendSequence(RawIrSequence.parseProntoCode("0000 006d 0022 0002 0152 00aa 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0696 0152 0055 0015 0e23"));
    }
}

