package cc.hyperium.gui;
import cc.hyperium.Hyperium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import java.io.IOException;

public class GuiHyperiumCredits extends HyperiumGui {
    private GuiScreen prevGui;
    private int offY = 0;
    public GuiHyperiumCredits(GuiScreen prevGui) {
        this.prevGui = prevGui;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        GlStateManager.scale(2f, 2f, 2f);
        drawCenteredString(fr, "Maintained by jumbo", width / 4, 20 + offY / 2, 0xFFFFFF);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
    }

    @Override
    protected void pack() {}

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i < 0) {
            offY -= 10;
        }
        else if (i > 0) {
            offY += 10;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            Hyperium.INSTANCE.getHandlers().getGuiDisplayHandler().setDisplayNextTick(prevGui);
        }
        super.keyTyped(typedChar, keyCode);
    }
}
