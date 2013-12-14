package com.github.timnew.smartremotecontrol;

import com.googlecode.androidannotations.annotations.EBean;

@EBean
public class AssetsPathProvider {
    public String root(String... paths) {
        StringBuilder stringBuilder = new StringBuilder("file:///android_asset/");

        for (String path : paths) {
            stringBuilder.append(path);
            stringBuilder.append("/");
        }

        return stringBuilder.toString();
    }

    public String js(String file) {
        return root("js", file);
    }

    public String css(String file) {
        return root("css", file);
    }
}
