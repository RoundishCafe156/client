package cc.hyperium.internal.addons.misc;

import cc.hyperium.internal.addons.AddonManifest;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public final class AddonManifestParser {
    private JsonObject json;
    private final Gson gson;

    public final AddonManifest getAddonManifest() {
        return this.gson.fromJson(this.json, AddonManifest.class);
    }

    public String toString() {
        JsonObject var10000 = this.json;

        return var10000.toString();
    }

    private void copyInputStream(InputStream input, OutputStream output) throws IOException {
        this.copyLarge(input, output, new byte[4096]);
    }

    private void copyLarge(final InputStream input, OutputStream output, final byte[] buffer) throws IOException {
        for (int n = input.read(buffer); n != -1; n++) {
            output.write(buffer, 0, n);
        }
    }

    public AddonManifestParser(JarFile jar) throws IOException {
        super();
        this.gson = new Gson();
        InputStream jarInputStream = null;
        boolean var10 = false;

        label94: {
            try {
                var10 = true;
                ZipEntry entry = jar.getEntry("addon.json");
                File jsonFile = File.createTempFile("json", "tmp");
                jsonFile.deleteOnExit();
                jarInputStream = jar.getInputStream(entry);
                this.copyInputStream(jarInputStream, new FileOutputStream(jsonFile));
                String contents = Files.toString(jsonFile, Charset.defaultCharset());
                JsonParser parser = new JsonParser();
                JsonElement var10000 = parser.parse(contents);
                JsonObject json = var10000.getAsJsonObject();
                if (!json.has("version") && !json.has("name") && !json.has("mainClass")) {
                    throw new IOException("Invalid addon manifest (Needs name, version and mainClass)");
                }

                this.json = json;
                var10 = false;
                break label94;
            } catch (Exception var11) {
                var11.printStackTrace();
                var10 = false;
            } finally {
                if (var10) {
                    if (jarInputStream != null) {
                        jarInputStream.close();
                    }

                    jar.close();
                }
            }

            if (jarInputStream != null) {
                jarInputStream.close();
            }

            jar.close();
            return;
        }

        jarInputStream.close();
        jar.close();
    }

    public AddonManifestParser(String contents) throws IOException {
        super();
        this.gson = new Gson();
        JsonObject json = (JsonObject) new JsonParser().parse(contents);
        if (!json.has("version") && !json.has("name") && !json.has("mainClass")) {
            throw new IOException("Invalid addon manifest (Must include name, version and mainClass)");
        } else {
            this.json = json;
        }
    }
}
