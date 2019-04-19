package cc.hyperium.internal.addons.strategy

import cc.hyperium.internal.addons.AddonBootstrap
import cc.hyperium.internal.addons.AddonManifest
import cc.hyperium.internal.addons.misc.AddonManifestParser
import org.apache.commons.io.IOUtils
import java.io.File
import java.nio.charset.Charset

class WorkspaceAddonLoader : AddonLoaderStrategy() {
    @Throws(Exception::class)
    override fun load(file: File?): AddonManifest? {
        val resource = javaClass.classLoader.getResource("addon.json") ?: return null // not in workspace
        if (javaClass.classLoader.getResource("pack.mcmeta") != null) {
            AddonBootstrap.addonResourcePacks.add(file)
        }
        val lines = IOUtils.toString(resource.openStream(), Charset.defaultCharset())
        val parser = AddonManifestParser(lines)
        return parser.getAddonManifest()
    }
}
