package cc.hyperium.gui;

import cc.hyperium.Hyperium;
import cc.hyperium.config.Settings;
import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.TickEvent;
import cc.hyperium.mixinsimp.entity.HyperiumEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;

public class BlurHandler {
    private boolean prevBlurOption;

    public BlurHandler() {
        prevBlurOption = Settings.BLUR_GUI;
    }

    @InvokeEvent
    private void onTick(TickEvent e) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.entityRenderer != null) {
            if (!Settings.MOTION_BLUR_ENABLED && Settings.BLUR_GUI && mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat) && !mc.entityRenderer.isShaderActive() && mc.theWorld != null) {
                HyperiumEntityRenderer.INSTANCE.enableBlurShader();
            }

            if(!Settings.MOTION_BLUR_ENABLED &&
                Settings.BLUR_GUI && mc.entityRenderer.isShaderActive() &&
                mc.currentScreen == null && mc.theWorld != null){

                HyperiumEntityRenderer.INSTANCE.disableBlurShader();
            }

            if (!Settings.MOTION_BLUR_ENABLED && !Settings.BLUR_GUI && prevBlurOption) {
                HyperiumEntityRenderer.INSTANCE.disableBlurShader();
            } else if (Settings.BLUR_GUI && !prevBlurOption) {
                if(!Settings.MOTION_BLUR_ENABLED) {
                    HyperiumEntityRenderer.INSTANCE.enableBlurShader();
                } else {
                    Hyperium.INSTANCE.getHandlers().getGeneralChatHandler().sendMessage("Warning: GUI blur will not take effect unless motion blur is disabled.",true);
                }
            }
            prevBlurOption = Settings.BLUR_GUI;
        }
    }
}
