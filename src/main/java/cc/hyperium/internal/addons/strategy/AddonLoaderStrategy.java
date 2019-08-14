package cc.hyperium.internal.addons.strategy;

import cc.hyperium.internal.addons.AddonManifest;
import java.io.File;

public abstract class AddonLoaderStrategy {
    public abstract AddonManifest load(File var1) throws Exception;
}
