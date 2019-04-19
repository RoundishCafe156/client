package cc.hyperium.utils;

import cc.hyperium.Hyperium;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import net.minecraft.client.Minecraft;
import rocks.rdil.jailbreak.util.OS;

public class LaunchUtil {
    public static void launch() {
        String cs = "";
        try {
            for (URL u : ((URLClassLoader) Hyperium.class.getClassLoader()).getURLs()) {
                if (u.getPath().contains("Hyperium")) cs = u.getPath();
            }
            Runtime.getRuntime().exec(new String[]{
                OS.isWindows() ? "cmd" : "bash",
                OS.isWindows() ? "/c" : "-c",
                "java",
                "-jar",
                cs,
                Minecraft.getMinecraft().mcDataDir.getAbsolutePath()
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
