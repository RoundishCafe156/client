package cc.hyperium.mixinsimp.renderer;

import cc.hyperium.event.EventBus;
import cc.hyperium.event.RenderPlayerEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;

public class HyperiumRenderPlayer {
    private RenderPlayer parent;

    public HyperiumRenderPlayer(RenderPlayer parent) {
        this.parent = parent;
    }

    public void doRender(AbstractClientPlayer entity, double x, double y, double z, float partialTicks, RenderManager renderManager) {
        EventBus.INSTANCE.post(new RenderPlayerEvent(entity, renderManager, x, y, z, partialTicks));
    }

    public void onUpdateTimer() {
        ModelPlayer modelplayer = parent.getMainModel();
        modelplayer.isRiding = modelplayer.isSneak = false;
    }
}
