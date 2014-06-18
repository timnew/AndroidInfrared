package com.github.timnew.shared.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseAdapter<T, TView extends View> extends android.widget.BaseAdapter {

    protected final Context context;

    protected BaseAdapter(Context applicationContext) {
        context = applicationContext;
    }

    @Override
    public abstract int getCount();

    @Override
    public abstract T getItem(int index);

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public TView getView(int index, View convertView, ViewGroup viewGroup) {
        TView view;

        if (convertView == null)
            view = createView(index, viewGroup);
        else
            //noinspection unchecked
            view = (TView) convertView;

        renderView(view, index);

        return view;
    }

    protected abstract TView createView(int index, ViewGroup viewGroup);

    protected abstract void renderView(TView view, int index);
}
