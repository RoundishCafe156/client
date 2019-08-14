package cc.hyperium.gui.main.components;

import cc.hyperium.gui.GuiHyperiumScreen;
import cc.hyperium.utils.HyperiumFontRenderer;
import cc.hyperium.utils.RenderUtils;
import java.awt.Color;
import java.util.function.Consumer;

public final class OverlaySlider extends OverlayLabel {
    private boolean updated;
    private float minVal;
    private float maxVal;
    private float value;
    private Consumer update;
    private boolean round;
    private boolean enabled;

    @SuppressWarnings("unchecked")
    public void handleMouseInput(int mouseX, int mouseY, int overlayX, int overlayY, int w, int h) {
        if (mouseX >= overlayX + w - 105 && mouseX <= overlayX + w - 5 && mouseY >= overlayY && mouseY <= overlayY + h) {
            int fx = mouseX - (overlayX + w - 105);
            this.value = (float)fx / 100.0F * (this.maxVal - this.minVal) + this.minVal;
            this.updated = false;
        } else if (!this.updated) {
            this.updated = true;
            this.update.accept(this.value);
        }
    }

    public boolean render(int mouseX, int mouseY, int overlayX, int overlayY, int w, int h, int overlayH) {
        if (!super.render(mouseX, mouseY, overlayX, overlayY, w, h, overlayH)) {
            return false;
        } else {
            float left = (float)(overlayX + w - 105);
            HyperiumFontRenderer fr = GuiHyperiumScreen.fr;
            String s = String.valueOf(this.value);
            if (this.round) {
                s = String.valueOf(Math.round(this.value));
            }

            float toFloat = (float)(overlayY + h / 2);
            int color = (int)4294967295L;
            if (!super.enabled) {
                color = (new Color(169, 169, 169)).getRGB();
            }

            fr.drawString(s, left - (float)5 - fr.getWidth(s), toFloat - (float)5, color);
            float rightSide = (float)(overlayX + w - 5);
            RenderUtils.drawLine(left, toFloat, rightSide, (float)(overlayY + h / 2), 2.0F, color);
            int toInt = (int)(left + (this.value - this.minVal) / (this.maxVal - this.minVal) * (float)100);
            RenderUtils.drawFilledCircle(toInt, overlayY + h / 2, 5.0F, color);
            return true;
        }
    }

    public final float getValue() {
        return this.value;
    }

    public final void setValue(float var1) {
        this.value = var1;
    }

    public final Consumer getUpdate() {
        return this.update;
    }

    public final void setUpdate(Consumer var1) {
        this.update = var1;
    }

    public final boolean getRound() {
        return this.round;
    }

    public final void setRound(boolean var1) {
        this.round = var1;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    private OverlaySlider(String label, float minVal, float maxVal, float value, Consumer update, boolean round, boolean enabled) {
        super(label, enabled, () -> {});
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.value = value;
        this.update = update;
        this.round = round;
        this.enabled = enabled;
    }

    public OverlaySlider(String label, float minVal, float maxVal, float value, Consumer update, boolean round) {
        this(label, minVal, maxVal, value, update, round, true);
    }
}
