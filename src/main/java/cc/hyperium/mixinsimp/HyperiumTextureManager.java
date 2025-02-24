package cc.hyperium.mixinsimp;

import cc.hyperium.event.EventBus;
import cc.hyperium.handlers.handlers.animation.cape.CapeHandler;
import cc.hyperium.utils.Utils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HyperiumTextureManager {
    public static HyperiumTextureManager INSTANCE;
    private TextureManager parent;
    private ConcurrentHashMap<String, ITextureObject> textures = new ConcurrentHashMap<>();

    public HyperiumTextureManager(TextureManager parent) {
        INSTANCE = this;
        this.parent = parent;
        EventBus.INSTANCE.register(this);
    }

    public boolean loadTickableTexture(ResourceLocation textureLocation, ITickableTextureObject textureObj, List<ITickable> listTickables) {
        if (parent.loadTexture(textureLocation, textureObj)) {
            listTickables.add(textureObj);
            textures.put(textureLocation.toString(), textureObj);
            return true;
        } else {
            return false;
        }
    }

    public void onResourceManagerReload(IResourceManager resourceManager) {
        CapeHandler.LOCK.lock();
        try {
            for (Map.Entry<String, ITextureObject> entry : textures.entrySet()) {
                String key = entry.getKey();
                ResourceLocation location;

                if (key.contains(":")) {
                    String[] split = key.split(":");
                    location = new ResourceLocation(split[0], split[1]);
                } else location = new ResourceLocation(key);

                String name = location.getResourcePath();

                // Prevent conflicts with OptiFine.
                if (name.startsWith("mcpatcher/") || name.startsWith("optifine/")) {
                    ITextureObject textureObject = textures.get(location);

                    if (textureObject instanceof AbstractTexture) {
                        AbstractTexture abstractTexture = (AbstractTexture) textureObject;
                        abstractTexture.deleteGlTexture();
                    }

                    continue;
                }

                parent.loadTexture(location, entry.getValue());
            }
            Utils.INSTANCE.setCursor(new ResourceLocation("textures/cursor.png"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CapeHandler.LOCK.unlock();
        }
    }

    public boolean loadTexture(ResourceLocation textureLocation, ITextureObject textureObj, IResourceManager theResourceManager) {
        ITextureObject textureCopy = textures.get(textureLocation.toString());
        if (textureCopy != null) {
            textureObj = textureCopy;
        }
        boolean flag = true;

        try {
            textureObj.loadTexture(theResourceManager);
        } catch (IOException ioexception) {
            textureObj = TextureUtil.missingTexture;
            textures.put(textureLocation.toString(), textureObj);
            flag = false;
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Registering texture");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Resource location being registered");
            crashreportcategory.addCrashSection("Resource location", textureLocation);
            String name = textureObj.getClass().getName();
            crashreportcategory.addCrashSectionCallable("Texture object class", () -> name);
            throw new ReportedException(crashreport);
        }

        textures.put(textureLocation.toString(), textureObj);
        return flag;
    }

    public ResourceLocation getDynamicTextureLocation(String name, DynamicTexture texture, Map<String, Integer> mapTextureCounters) {
        Integer integer = mapTextureCounters.get(name);

        if (integer == null) {
            integer = 1;
        } else {
            integer = integer + 1;
        }

        mapTextureCounters.put(name, integer);
        String format = String.format("dynamic/%s_%d", name, integer);
        ResourceLocation resourcelocation = new ResourceLocation(format);
        textures.put(resourcelocation.toString(), texture);
        parent.loadTexture(resourcelocation, texture);
        return resourcelocation;
    }

    public void bindTexture(ResourceLocation resource) {
        ITextureObject itextureobject = textures.get(resource.toString());

        if (itextureobject == null) {
            itextureobject = new SimpleTexture(resource);
            parent.loadTexture(resource, itextureobject);
        }

        GlStateManager.bindTexture(itextureobject.getGlTextureId());
    }


    public ITextureObject getTexture(ResourceLocation textureLocation) {
        if (textureLocation == null) {
            return null;
        }
        return textures.get(textureLocation.toString());
    }

    public void tick(List<ITickable> listTickables) {
        for (ITickable itickable : listTickables) {
            itickable.tick();
        }
    }

    public void deleteTexture(ResourceLocation textureLocation) {
        ITextureObject itextureobject = this.getTexture(textureLocation);
        if (itextureobject != null) TextureUtil.deleteTexture(itextureobject.getGlTextureId());
        if (textureLocation != null) textures.remove(textureLocation.toString());
    }
}
