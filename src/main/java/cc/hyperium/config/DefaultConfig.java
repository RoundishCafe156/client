/*
 *     Copyright (C) 2018  Hyperium <https://hyperium.cc/>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cc.hyperium.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultConfig {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final List<Object> configObjects = new ArrayList<>();
    private final File file;
    private JsonObject config = new JsonObject();

    public DefaultConfig(File configFile) {
        this.file = configFile;
        try {
            if (configFile.exists()) {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null)
                    builder.append(line);

                String done = builder.toString();
                config = new JsonParser().parse(done).getAsJsonObject();
            } else {
                config = new JsonObject();
                saveFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(gson.toJson(config));
            bw.close();
            fw.close();
        } catch (Exception ignored) {}
    }

    public void save() {
        for (Object o : configObjects)
            loadToJson(o);
        saveFile();
    }

    public Object register(Object object) {
        if (Arrays.stream(object.getClass().getDeclaredFields()).noneMatch(f -> f.isAnnotationPresent(ConfigOpt.class))) {
            return object;
        }
        if (object instanceof PreConfigHandler)
            ((PreConfigHandler) object).preUpdate();
        loadToClassObject(object);
        configObjects.add(object);
        if (object instanceof PostConfigHandler)
            ((PostConfigHandler) object).postUpdate();
        return object;
    }

    private void loadToClassObject(Object object) {
        Class<?> c = object.getClass();
        if (!config.has(c.getName())) config.add(c.getName(), new JsonObject());
        Arrays.stream(c.getDeclaredFields()).filter(f -> f.isAnnotationPresent(ConfigOpt.class) && config.has(c.getName())).forEach(f -> {
            f.setAccessible(true);
            JsonObject tmp = config.get(c.getName()).getAsJsonObject();
            if (tmp.has(f.getName())) {
                try {
                    f.set(object, gson.fromJson(tmp.get(f.getName()), f.getType()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadToJson(Object object) {
        if (object instanceof PreSaveHandler) {
            ((PreSaveHandler) object).preSave();
        }
        Class<?> c = object.getClass();
        Arrays.stream(c.getDeclaredFields()).filter(f -> f.isAnnotationPresent(ConfigOpt.class) && config.has(c.getName())).forEach(f -> {
            f.setAccessible(true);
            JsonObject classObject = config.get(c.getName()).getAsJsonObject();
            try {
                classObject.add(f.getName(), gson.toJsonTree(f.get(object), f.getType()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public JsonObject getConfig() {
        return config;
    }
}
