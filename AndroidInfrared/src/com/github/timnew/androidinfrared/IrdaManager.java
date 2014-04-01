package com.github.timnew.androidinfrared;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IrdaManager {

    public static final String IRDA_SERVICE = "irda";


    public static IrdaManager getIrdaManager(Context applicationContext) {
        Object irdaService = applicationContext.getSystemService(IRDA_SERVICE);

        if (irdaService == null)
            return null;

        try {
            return new IrdaManager(irdaService);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    private final Object irdaService;
    private final Method writeIrSendMethod;

    private IrdaManager(Object irdaService) throws NoSuchMethodException {
        this.irdaService = irdaService;

        Class<?> irdaServiceClass = irdaService.getClass();

        writeIrSendMethod = irdaServiceClass.getMethod("write_irsend", String.class);
    }

    public IrdaManager sendSequence(String sequence) {
        Log.d("IRCode", sequence);
        rawWrite(sequence);
        return this;
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

}
