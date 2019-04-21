package cc.hyperium.mixinsimp.renderer.model;

import cc.hyperium.mixins.renderer.model.MixinModelBiped;
import net.minecraft.client.model.ModelRenderer;

public interface IMixinModelBiped {
    ModelRenderer getBipedRightUpperLeg();

    ModelRenderer getBipedRightLowerLeg();

    ModelRenderer getBipedLeftUpperLeg();

    ModelRenderer getBipedLeftLowerLeg();

    ModelRenderer getBipedRightUpperArm();

    ModelRenderer getBipedRightForeArm();

    ModelRenderer getBipedLeftUpperArm();

    ModelRenderer getBipedLeftForeArm();

    ModelRenderer getBipedBody();

    ModelRenderer getBipedHead();

    ModelRenderer getBipedHeadwear();
}
