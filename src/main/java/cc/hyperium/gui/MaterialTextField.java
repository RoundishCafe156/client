package cc.hyperium.gui;

import cc.hyperium.utils.HyperiumFontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatAllowedCharacters;

public class MaterialTextField {
    private int x, y, width, height;
    private HyperiumFontRenderer fr;
    private String text = "", hint;
    private boolean focused;
    private int blink;

    public MaterialTextField(int x, int y, int width, int height, String hint, HyperiumFontRenderer fr) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hint = hint;
        this.fr = fr;
    }

    public void render(int mx, int my) {
        boolean hover = mx > this.x && my > this.y && mx < this.x + width && my < this.y + height;
        Gui.drawRect(x, y + height - 1, x + width, y + height, hover || focused ? 0xffffffff : 0xff969696);
        boolean em = text.isEmpty();
        fr.drawString(em && !focused ? hint : text, x + 2, y + height / 2f - fr.FONT_HEIGHT / 2f, em ? 0xff969696 : 0xffffffff);
        int x = (int) (this.x + 3 + fr.getWidth(text));
        if (focused && blink >= 10) {
            Gui.drawRect(x, y + 4, x + 1, y + height - 4, 0xffffffff);
            if (blink >= 20)
                blink = -1;
        }
    }

    public void update() {
        blink++;
        if (blink >= 20)
            blink = -1;
    }

    public void onClick(int x, int y, int mb) {
        if (mb == 0)
            focused = x > this.x && y > this.y && x < this.x + width && y < this.y + height;
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (focused)
            if (keyCode == 28)
                focused = false;
            else if (keyCode == 14) {
                if (!text.isEmpty())
                    text = text.substring(0, text.length() - 1);
            } else if (ChatAllowedCharacters.isAllowedCharacter(typedChar))
                text += typedChar;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
