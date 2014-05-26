package com.github.timnew.androidinfrared;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class ConsumerIrManagerCompat extends ConsumerIrManager {

    private final android.hardware.ConsumerIrManager service;

    public ConsumerIrManagerCompat(Context context) {
        service = (android.hardware.ConsumerIrManager) context.getSystemService(Context.CONSUMER_IR_SERVICE);
    }

    @Override
    public boolean hasIrEmitter() {
        return service.hasIrEmitter();
    }

    @Override
    public void transmit(int carrierFrequency, int[] pattern) {
        service.transmit(carrierFrequency, pattern);
    }

    @Override
    public android.hardware.ConsumerIrManager.CarrierFrequencyRange[] getCarrierFrequencies() {
        return service.getCarrierFrequencies();
    }
}
