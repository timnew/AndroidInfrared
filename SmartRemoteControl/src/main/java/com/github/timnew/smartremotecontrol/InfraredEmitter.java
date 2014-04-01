package com.github.timnew.smartremotecontrol;

import android.content.Context;
import android.util.Log;

import com.github.timnew.androidinfrared.IrdaManager;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.api.Scope;

import static com.github.timnew.androidinfrared.IrdaProtocols.DISH.buildDISH;
import static com.github.timnew.androidinfrared.IrdaProtocols.JVC.buildJVC;
import static com.github.timnew.androidinfrared.IrdaProtocols.NEC.buildNEC;
import static com.github.timnew.androidinfrared.IrdaProtocols.Panasonic.buildPanasonic;
import static com.github.timnew.androidinfrared.IrdaProtocols.RC5.buildRC5;
import static com.github.timnew.androidinfrared.IrdaProtocols.RC6.buildRC6;
import static com.github.timnew.androidinfrared.IrdaProtocols.Sharp.buildSharp;
import static com.github.timnew.androidinfrared.IrdaProtocols.Sony.buildSony;
import static java.lang.String.format;

@EBean(scope = Scope.Singleton)
public class InfraredEmitter {

    public static final String IR_COMMAND = "IRCommand";
    @RootContext
    protected Context context;

    private IrdaManager manager;

    @AfterInject
    protected void afterInjects() {
        manager = IrdaManager.getIrdaManager(context);
    }

    public boolean isIrdaSupported() {
        return manager != null;
    }

    private void logAction(String type, int size, long data) {
        Log.i(IR_COMMAND, format("[%s](%d bits): 0x%X", type, size, data));
    }

    private void logActionWithAddress(String type, int address, long data) {
        Log.i(IR_COMMAND, format("[%s]: Address: 0x%X Data: 0x%X", type, address, data));
    }

    public void NEC(int size, long data) {
        logAction("NEC", size, data);
        manager.sendSequence(buildNEC(size, (int) data));
    }

    public void Sony(int size, long data) {
        logAction("Sony", size, data);
        manager.sendSequence(buildSony(size, data));
    }

    public void RC5(int size, long data) {
        logAction("RC", size, data);
        manager.sendSequence(buildRC5(size, data));
    }

    public void RC6(int size, long data) {
        logAction("RC", size, data);
        manager.sendSequence(buildRC6(size, data));
    }

    public void DISH(int size, long data) {
        logAction("DISH", size, data);
        manager.sendSequence(buildDISH(size, (int) data));
    }

    public void Sharp(int size, long data) {
        logAction("Sharp", size, data);
        manager.sendSequence(buildSharp(size, (int) data));
    }

    public void Panasonic(int address, long data) {
        logActionWithAddress("Panasonic", address, data);
        manager.sendSequence(buildPanasonic(address, (int) data));
    }

    public void JVC(int size, long data) {
        logAction("JVC", size, data);
        manager.sendSequence(buildJVC(size, data, false));
    }
}
