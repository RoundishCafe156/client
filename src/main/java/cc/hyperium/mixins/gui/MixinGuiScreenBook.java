package cc.hyperium.mixins.gui;

import net.minecraft.client.gui.GuiScreenBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiScreenBook.class)
public interface MixinGuiScreenBook {
    @Accessor
    NBTTagList getBookPages();

    @Accessor
    int getCurrPage();
}
