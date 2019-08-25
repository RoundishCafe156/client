package cc.hyperium.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public enum Icons {
    SETTINGS(new ResourceLocation("textures/material/settings.png")),
    EXTENSION(new ResourceLocation("textures/material/extension.png")),
    EXIT(new ResourceLocation("textures/material/exit.png"));

    private ResourceLocation res;
    Icons(ResourceLocation res) {
        this.res = res;
    }
    public void bind() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(res);
    }
}
