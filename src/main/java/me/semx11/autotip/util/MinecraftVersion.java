package me.semx11.autotip.util;

public enum MinecraftVersion {
    V1_8, V1_8_9;
    public String toString() {
        return this.name().substring(1).replaceAll("_", ".");
    }
}
