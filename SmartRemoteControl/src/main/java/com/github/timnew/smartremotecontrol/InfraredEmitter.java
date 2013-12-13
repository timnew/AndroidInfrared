package com.github.timnew.smartremotecontrol;

import android.content.Context;

import com.github.timnew.androidinfrared.IrdaManager;
import com.github.timnew.androidinfrared.IrdaProtocols;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.api.Scope;

@EBean(scope = Scope.Singleton)
public class InfraredEmitter {

    @RootContext
    protected Context context;

    private IrdaManager manager;

    @AfterInject
    protected void afterInjects(){
        manager = IrdaManager.getIrdaManager(context);
    }

    public void NEC(int size, int data) { manager.sendSequence(IrdaProtocols.NEC.buildNEC(size,data)); }
    public void Sony(int size, int data) { manager.sendSequence(IrdaProtocols.Sony.buildSony(size,data)); }
    public void RC5(int size, int data) { manager.sendSequence(IrdaProtocols.RC5.buildRC5(size,data)); }
    public void RC6(int size, int data) { manager.sendSequence(IrdaProtocols.RC6.buildRC6(size,data)); }
    public void DISH(int size, int data) { manager.sendSequence(IrdaProtocols.DISH.buildDISH(size,data)); }
    public void Sharp(int size, int data) { manager.sendSequence(IrdaProtocols.Sharp.buildSharp(size,data)); }
    public void Panasonic(int address, int data) { manager.sendSequence(IrdaProtocols.Panasonic.buildPanasonic(address,data)); }
    public void JVC(int size, int data) { manager.sendSequence(IrdaProtocols.JVC.buildJVC(size,data, false)); }

}
