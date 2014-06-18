package com.github.timnew.shared.adapters;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class ListAdapter<T, TView extends View> extends BaseAdapter<T, TView> {

    protected List<T> list;

    protected ListAdapter(Context applicationContext) {
        this(applicationContext, new ArrayList<T>());
    }

    protected ListAdapter(Context applicationContext, List<T> list) {
        super(applicationContext);

        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int index) {
        return list.get(index);
    }
}
