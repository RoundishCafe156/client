package cc.hyperium.mods.blockoverlay;

public enum BlockOverlayMode {
    NONE("None"),
    DEFAULT("Default"),
    OUTLINE("Outline"),
    FULL("Full");

    private String name;

    BlockOverlayMode(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
