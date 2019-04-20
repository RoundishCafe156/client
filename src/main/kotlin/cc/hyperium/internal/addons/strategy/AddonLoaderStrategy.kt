package cc.hyperium.internal.addons.strategy

import cc.hyperium.internal.addons.AddonManifest
import java.io.File

abstract class AddonLoaderStrategy {
    @Throws(Exception::class)
    abstract fun load(file: File?): AddonManifest?
}
