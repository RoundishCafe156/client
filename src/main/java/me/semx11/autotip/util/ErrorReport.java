package me.semx11.autotip.util;

import me.semx11.autotip.Autotip;

public class ErrorReport {
    public static void reportException(Throwable t) {
        Autotip.LOGGER.error(t.getMessage(), t);
    }
}
