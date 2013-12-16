package com.github.timnew.smartremotecontrol;

import android.content.Context;
import android.util.Log;

import com.github.timnew.androidinfrared.IrdaManager;
import com.github.timnew.androidinfrared.IrdaProtocols;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.api.Scope;

import static java.lang.String.format;

@EBean(scope = Scope.Singleton)
public class InfraredEmitter {

    @RootContext
    protected Context context;

    private IrdaManager manager;

    @AfterInject
    protected void afterInjects() {
        manager = IrdaManager.getIrdaManager(context);
    }

    private void logAction(String type, int size, int data) {
        Log.i("IRCommand", format("[%s](%d bits): 0x%X", type, size, data));
    }

    private void logActionWithAddress(String type, int address, int data) {
        Log.i("IRCommand", format("[%s]: Address: 0x%X Data: 0x%X", type, address, data));
    }

    public void NEC(int size, int data) {
        logAction("NEC", size, data);
        try {
            manager.sendSequence(IrdaProtocols.NEC.buildNEC(size, data));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Sony(int size, int data) {
        logAction("Sony", size, data);
        manager.sendSequence(IrdaProtocols.Sony.buildSony(size, data));
    }

    public void RC5(int size, int data) {
        logAction("RC", size, data);
        manager.sendSequence(IrdaProtocols.RC5.buildRC5(size, data));
    }

    public void RC6(int size, int data) {
        logAction("RC", size, data);
        manager.sendSequence(IrdaProtocols.RC6.buildRC6(size, data));
    }

    public void DISH(int size, int data) {
        logAction("DISH", size, data);
        manager.sendSequence(IrdaProtocols.DISH.buildDISH(size, data));
    }

    public void Sharp(int size, int data) {
        logAction("Sharp", size, data);
        manager.sendSequence(IrdaProtocols.Sharp.buildSharp(size, data));
    }

    public void Panasonic(int address, int data) {
        logActionWithAddress("Panasonic", address, data);
        manager.sendSequence(IrdaProtocols.Panasonic.buildPanasonic(address, data));
    }

    public void JVC(int size, int data) {
        logAction("JVC", size, data);
        manager.sendSequence(IrdaProtocols.JVC.buildJVC(size, data, false));
    }

}
