package dev.gavhack.settings;

public class EnumSetting<T extends Enum<T>> extends Setting {

    private final T[] values;
    private final T defaultValue;
    private T value;

    @SuppressWarnings("unchecked")
    public EnumSetting(String name, T value) {
        super(name);
        this.defaultValue = value;
        this.value = value;
        this.values = (T[]) value.getClass().getEnumConstants();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T t) {
        this.value = t;
    }

    public T increment() {
        if (value.ordinal() == values.length - 1) {
            value = values[0];
        } else {
            value = values[value.ordinal() + 1];
        }

        return value;
    }

    public T decrement() {
        if (value.ordinal() == 0) {
            value = values[values.length - 1];
        } else {
            value = values[value.ordinal() - 1];
        }
        return value;
    }

    @Override
    public void reset() {
        this.value = this.defaultValue;
    }

    public int indexOf(T t) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == t)
                return i;
        }

        return -1;
    }

    public T[] getValues() {
        return values;
    }
}
