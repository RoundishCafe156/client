package cc.hyperium.handlers.handlers.particle;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import java.util.List;

public abstract class AbstractAnimation {
    public abstract List<Vec3> render(EntityPlayer player, double x, double y, double z);
}
