package com.github.timnew.androidinfrared;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ConsumerIrManagerSamsung extends ConsumerIrManager {

    public static final int MICRO_SECONDS_IN_A_SECOND = 1000000;

    public static ConsumerIrManagerSamsung getIrdaManager(Context applicationContext) {
        Object irdaService = applicationContext.getSystemService(Context.CONSUMER_IR_SERVICE);

        if (irdaService == null)
            return null;

        return new ConsumerIrManagerSamsung(irdaService);
    }

    private final Object irdaService;
    private final Method writeIrSendMethod;

    private ConsumerIrManagerSamsung(Object irdaService) {
        this.irdaService = irdaService;

        Class<?> irdaServiceClass = irdaService.getClass();

        Method reflectedMethod;

        try {
            reflectedMethod = irdaServiceClass.getMethod("write_irsend", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();

            reflectedMethod = null;
        }

        writeIrSendMethod = reflectedMethod;
    }

    private void rawWrite(String code) {
        try {
            writeIrSendMethod.invoke(irdaService, code);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasIrEmitter() {
        return writeIrSendMethod != null;
    }

    @Override
    public void transmit(int carrierFrequency, int[] pattern) {
        if (!hasIrEmitter())
            return;

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(carrierFrequency);

        for (int bit : pattern) {
            stringBuilder.append(',');
            stringBuilder.append(bit * carrierFrequency / MICRO_SECONDS_IN_A_SECOND);
        }


        rawWrite(stringBuilder.toString());
    }

    @Override
    public android.hardware.ConsumerIrManager.CarrierFrequencyRange[] getCarrierFrequencies() {
        return null; // 36khz - 40khz
    }
}
