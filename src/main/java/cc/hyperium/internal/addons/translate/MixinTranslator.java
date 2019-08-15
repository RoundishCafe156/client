package cc.hyperium.internal.addons.translate;

import cc.hyperium.internal.addons.AddonManifest;
import java.util.List;
import org.spongepowered.asm.mixin.Mixins;

public final class MixinTranslator implements ITranslator {
    public void translate(AddonManifest manifest) {
        List var10000 = manifest.getMixinConfigs();
        if (var10000 != null) {
            for (Object element$iv : var10000) {
                String p1 = (String) element$iv;
                Mixins.addConfiguration(p1);
            }
        }
    }
}
