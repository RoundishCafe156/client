package cc.hyperium.handlers.handlers.animation.fortnite;

import cc.hyperium.handlers.handlers.animation.AnimatedDance;
import cc.hyperium.utils.JsonHolder;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FortniteDefaultDance extends AnimatedDance {
    @Override
    public JsonHolder getData() {
        try {
            return new JsonHolder(IOUtils.toString(new URL("https://raw.githubusercontent.com/hyperiumjailbreak/tools/master/json_dumps/fn.json"), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonHolder();
    }
}
