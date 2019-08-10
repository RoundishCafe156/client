package cc.hyperium.mixinsimp.renderer.model;

import cc.hyperium.mixins.renderer.model.MixinModelPlayer;
import net.minecraft.client.model.ModelRenderer;

public interface IMixinModelPlayer extends IMixinModelBiped {
    ModelRenderer getBipedRightUpperLegwear();

    ModelRenderer getBipedRightLowerLegwear();

    ModelRenderer getBipedLeftUpperLegwear();

    ModelRenderer getBipedLeftLowerLegwear();

    ModelRenderer getBipedRightUpperArmwear();

    ModelRenderer getBipedRightForeArmwear();

    ModelRenderer getBipedLeftUpperArmwear();

    ModelRenderer getBipedLeftForeArmwear();

    ModelRenderer getBipedBodywear();

    ModelRenderer getCape();
}
