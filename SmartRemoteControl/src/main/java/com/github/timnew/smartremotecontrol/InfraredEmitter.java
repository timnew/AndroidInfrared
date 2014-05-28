package com.github.timnew.smartremotecontrol;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.github.timnew.androidinfrared.ConsumerIrManager;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.api.Scope;

import static com.github.timnew.androidinfrared.ConsumerIrManager.getSupportConsumerIrManager;
import static com.github.timnew.androidinfrared.IrCommand.DISH.buildDISH;
import static com.github.timnew.androidinfrared.IrCommand.JVC.buildJVC;
import static com.github.timnew.androidinfrared.IrCommand.NEC.buildNEC;
import static com.github.timnew.androidinfrared.IrCommand.Panasonic.buildPanasonic;
import static com.github.timnew.androidinfrared.IrCommand.RC5.buildRC5;
import static com.github.timnew.androidinfrared.IrCommand.RC6.buildRC6;
import static com.github.timnew.androidinfrared.IrCommand.Sharp.buildSharp;
import static com.github.timnew.androidinfrared.IrCommand.Sony.buildSony;
import static java.lang.String.format;

@EBean(scope = Scope.Singleton)
public class InfraredEmitter {

    public static final String IR_COMMAND = "IRCommand";
    @RootContext
    protected Context context;

    private ConsumerIrManager manager;

    @AfterInject
    protected void afterInjects() {
        manager = getSupportConsumerIrManager(context);
    }

    public boolean hasIrEmitter() {
        return manager.hasIrEmitter();
    }

    private void logAction(String type, int size, long data) {
        Log.i(IR_COMMAND, format("[%s](%d bits): 0x%X", type, size, data));
    }

    private void logActionWithAddress(String type, int address, long data) {
        Log.i(IR_COMMAND, format("[%s]: Address: 0x%X Data: 0x%X", type, address, data));
    }

    @JavascriptInterface
    public void NEC(int size, long data) {
        logAction("NEC", size, data);
        manager.transmit(buildNEC(size, (int) data));
    }

    @JavascriptInterface
    public void Sony(int size, long data) {
        logAction("Sony", size, data);
        manager.transmit(buildSony(size, data));
    }

    @JavascriptInterface
    public void RC5(int size, long data) {
        logAction("RC", size, data);
        manager.transmit(buildRC5(size, data));
    }

    @JavascriptInterface
    public void RC6(int size, long data) {
        logAction("RC", size, data);
        manager.transmit(buildRC6(size, data));
    }

    @JavascriptInterface
    public void DISH(int size, long data) {
        logAction("DISH", size, data);
        manager.transmit(buildDISH(size, (int) data));
    }

    @JavascriptInterface
    public void Sharp(int size, long data) {
        logAction("Sharp", size, data);
        manager.transmit(buildSharp(size, (int) data));
    }

    @JavascriptInterface
    public void Panasonic(int address, long data) {
        logActionWithAddress("Panasonic", address, data);
        manager.transmit(buildPanasonic(address, (int) data));
    }

    @JavascriptInterface
    public void JVC(int size, long data) {
        logAction("JVC", size, data);
        manager.transmit(buildJVC(size, data, false));
    }
}
