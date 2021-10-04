package me.gavin.gavhack.misc;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {

    public static float roundNumber(float value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        final BigDecimal bd = new BigDecimal(value).setScale(places, RoundingMode.UP);
        return bd.floatValue();
    }
}
