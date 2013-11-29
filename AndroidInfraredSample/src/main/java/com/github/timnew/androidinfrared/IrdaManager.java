package com.github.timnew.androidinfrared;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IrdaManager {

    public static IrdaManager getIrdaManager(Context applicationContext) {
        Object irdaService = applicationContext.getSystemService("irda");

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

    private final Method readIrSendMethod;

    private IrdaManager(Object irdaService) throws NoSuchMethodException {
        this.irdaService = irdaService;

        Class<?> irdaServiceClass = irdaService.getClass();

        writeIrSendMethod = irdaServiceClass.getMethod("write_irsend", String.class);
        readIrSendMethod = irdaServiceClass.getMethod("read_irsend");
    }

    public IrdaManager sendSequence(IrSequence sequence) {
        String code = sequence.getCode();
        Log.d("IRCode", code);
        rawWrite(code);
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

    private String rawRead() {
        try {
            return (String) readIrSendMethod.invoke(irdaService);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

}
