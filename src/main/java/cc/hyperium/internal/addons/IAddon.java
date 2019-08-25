package cc.hyperium.internal.addons;

public interface IAddon {
    void onLoad();
    void onClose();
    @Deprecated default void sendDebugInfo() {}
}
