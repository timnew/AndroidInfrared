package com.github.timnew.androidinfrared;

public abstract class IrSequence {
    public abstract String getName();

    protected String code;

    protected abstract CharSequence generateCode();

    public String getCode() {
        if (code != null)
            return code;

        return code = generateCode().toString();
    }

    @Override
    public String toString() {
        return String.format("%s<%s>", getName(), getCode());
    }
}
