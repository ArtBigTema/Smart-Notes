package av.smartnotes.substance;

import android.graphics.Color;

/**
 * Created by Artem on 17.01.2017.
 */

public enum Priority {
    Blue(Color.BLUE), Green(Color.GREEN), Yellow(Color.YELLOW), Red(Color.RED);
    private int mValue;

    Priority(int value) {
        this.mValue = value;
    }

    public int id() {
        return mValue;
    }

    public static Priority fromId(int value) {
        for (Priority color : values()) {
            if (color.mValue == value) {
                return color;
            }
        }
        return Blue;
    }
}