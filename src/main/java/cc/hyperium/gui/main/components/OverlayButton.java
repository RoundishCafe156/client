package cc.hyperium.gui.main.components;

public final class OverlayButton extends OverlayLabel {
    private final Runnable callback;

    public void mouseClicked(int mouseX, int mouseY, int overlayX, int overlayY, int w, int h) {
        if (mouseX >= overlayX && mouseX <= overlayX + w && mouseY >= overlayY && mouseY <= overlayY + h) {
            this.callback.run();
        }
    }

    public final Runnable getCallback() {
        return this.callback;
    }

    public OverlayButton(String label, Runnable callback) {
        super(label, true, () -> {});
        this.callback = callback;
    }
}
