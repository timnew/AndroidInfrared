package com.github.timnew.smartremotecontrol;

import android.content.Context;
import android.util.Log;

import com.github.timnew.androidinfrared.ConsumerIrManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import static com.github.timnew.androidinfrared.ConsumerIrManager.getSupportConsumerIrManager;
import static com.github.timnew.androidinfrared.IrCommand.DISH.buildDISH;
import static com.github.timnew.androidinfrared.IrCommand.JVC.buildJVC;
import static com.github.timnew.androidinfrared.IrCommand.NEC.buildNEC;
import static com.github.timnew.androidinfrared.IrCommand.Panasonic.buildPanasonic;
import static com.github.timnew.androidinfrared.IrCommand.Pronto.buildPronto;
import static com.github.timnew.androidinfrared.IrCommand.RC5.buildRC5;
import static com.github.timnew.androidinfrared.IrCommand.RC6.buildRC6;
import static com.github.timnew.androidinfrared.IrCommand.Sharp.buildSharp;
import static com.github.timnew.androidinfrared.IrCommand.Sony.buildSony;
import static java.lang.String.format;
import static org.androidannotations.annotations.EBean.Scope.Singleton;

@EBean(scope = Singleton)
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


    private void logAction(String type, String data) {
        Log.i(IR_COMMAND, format("[%s]: %s", type, data));
    }

    private void logAction(String type, int size, long data) {
        Log.i(IR_COMMAND, format("[%s](%d bits): 0x%X", type, size, data));
    }

    private void logActionWithAddress(String type, int address, long data) {
        Log.i(IR_COMMAND, format("[%s]: Address: 0x%X Data: 0x%X", type, address, data));
    }

    public void NEC(int size, long data) {
        logAction("NEC", size, data);
        manager.transmit(buildNEC(size, (int) data));
    }

    public void Sony(int size, long data) {
        logAction("Sony", size, data);
        manager.transmit(buildSony(size, data));
    }

    public void RC5(int size, long data) {
        logAction("RC", size, data);
        manager.transmit(buildRC5(size, data));
    }

    public void RC6(int size, long data) {
        logAction("RC", size, data);
        manager.transmit(buildRC6(size, data));
    }

    public void DISH(int size, long data) {
        logAction("DISH", size, data);
        manager.transmit(buildDISH(size, (int) data));
    }

    public void Sharp(int size, long data) {
        logAction("Sharp", size, data);
        manager.transmit(buildSharp(size, (int) data));
    }

    public void Panasonic(int address, long data) {
        logActionWithAddress("Panasonic", address, data);
        manager.transmit(buildPanasonic(address, (int) data));
    }

    public void JVC(int size, long data) {
        logAction("JVC", size, data);
        manager.transmit(buildJVC(size, data, false));
    }

    public void Proton(String data) {
        logAction("Pronto", data);
        manager.transmit(buildPronto(data));
    }
}
