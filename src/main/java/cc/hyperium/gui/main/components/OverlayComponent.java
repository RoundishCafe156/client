package cc.hyperium.gui.main.components;

import cc.hyperium.utils.HyperiumFontRenderer;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import java.awt.Font;

public abstract class OverlayComponent {
    private static final HyperiumFontRenderer fr = new HyperiumFontRenderer("Arial", Font.PLAIN, 20);

    String label;
    boolean enabled;

    public OverlayComponent(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean render(int mouseX, int mouseY, int overlayX, int overlayY, int w, int h, int overlayH) {
        int textY = (overlayY + (h - fr.FONT_HEIGHT) / 2);
        if (textY < (overlayH / 4)) {
            return false;
        } else if ((textY + h) > (overlayH / 4 * 3)) {
            return false;
        }
        if (mouseX >= overlayX && mouseX <= overlayX + w && mouseY >= overlayY && mouseY <= overlayY + h) {
            Gui.drawRect(overlayX, overlayY, overlayX + w, overlayY + h, 0x1e000000);
        }
        if (enabled) {
            fr.drawString(label, overlayX + 4,
                (overlayY + (h - fr.FONT_HEIGHT) / 2f), 0xffffff);
        } else {
            fr.drawString(label, overlayX + 4,
                (overlayY + (h - fr.FONT_HEIGHT) / 2f), new Color(169, 169, 169).getRGB());
        }
        return true;
    }

    public abstract void mouseClicked(int mouseX, int mouseY, int overlayX, int overlayY, int w, int h);

    public void handleMouseInput(int mouseX, int mouseY, int overlayX, int overlayY, int w, int h) {}

    public String getLabel() {
        return this.label;
    }
}
