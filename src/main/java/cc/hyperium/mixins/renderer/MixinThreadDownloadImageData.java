package cc.hyperium.mixins.renderer;

import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import java.io.File;

@Mixin(ThreadDownloadImageData.class)
public abstract class MixinThreadDownloadImageData extends SimpleTexture {
    @Shadow
    @Final
    private String imageUrl;

    @Shadow
    @Final
    private File cacheFile;

    @Shadow
    @Final
    private IImageBuffer imageBuffer;

    public MixinThreadDownloadImageData(ResourceLocation textureResourceLocation) {
        super(textureResourceLocation);
    }

    @Overwrite
    protected void loadTextureFromServer() {
        CachedThreadDownloader cachedThreadDownloader = new CachedThreadDownloader(imageUrl, cacheFile, imageBuffer, threadDownloadImageData, textureLocation);
        cachedThreadDownloader.process();
    }
}
