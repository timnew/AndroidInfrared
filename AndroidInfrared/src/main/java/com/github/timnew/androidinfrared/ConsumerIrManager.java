package com.github.timnew.androidinfrared;

import android.content.Context;
import android.os.Build;

public abstract class ConsumerIrManager {

    public boolean hasIrEmitter() {
        return false;
    }

    public void transmit(int carrierFrequency, int[] pattern) {
    }

    public void transmit(IrCommand command) {
        transmit(command.frequency, command.pattern);
    }

    public android.hardware.ConsumerIrManager.CarrierFrequencyRange[] getCarrierFrequencies() {
        return null;
    }

    public static ConsumerIrManager getSupportConsumerIrManager(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new ConsumerIrManagerCompat(context);
        }

        ConsumerIrManager consumerIrManagerSamsung = ConsumerIrManagerSamsung.getSupportConsumerIrManager(context);

        if (consumerIrManagerSamsung != null)
            return consumerIrManagerSamsung;

        return new ConsumerIrManager() {
        };
    }

}

