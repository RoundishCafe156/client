package cc.hyperium.mixinsimp;

import cc.hyperium.config.Settings;
import net.minecraft.util.StatCollector;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class HyperiumEnchantment {
    public void getTranslatedName(int level, CallbackInfoReturnable<String> ci, String name) {
        if (!Settings.ROMAN_NUMERALS) ci.setReturnValue(StatCollector.translateToLocal(name) + " " + level);
    }
}
