package cc.hyperium.handlers.handlers.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public interface IParticle {
    EntityFX spawn(World world, double x, double y, double z);
}
