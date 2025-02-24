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

package cc.hyperium.commands.defaults;
import cc.hyperium.Hyperium;
import cc.hyperium.commands.BaseCommand;
import cc.hyperium.handlers.handlers.HypixelDetector;
import cc.hyperium.handlers.handlers.chat.GeneralChatHandler;
import cc.hyperium.mods.chromahud.ChromaHUDApi;
import cc.hyperium.network.NetworkHandler;
import cc.hyperium.purchases.HyperiumPurchase;
import cc.hyperium.purchases.PurchaseApi;
import cc.hyperium.utils.JsonHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

public class CommandDebug implements BaseCommand {
    private static final Gson printer = new GsonBuilder().setPrettyPrinting().create();

    private static void tryChromaHUD(StringBuilder builder) {
        try {
            builder.append("ChromaHUD: ").append(printer.toJson(ChromaHUDApi.getInstance().getConfig().getObject()));
        } catch (Exception e) {
            builder.append("ChromaHUD: Error");
        }
    }

    private static void tryConfig(StringBuilder builder) {
        try {
            Hyperium.CONFIG.save();
            builder.append("Config: ").append(printer.toJson(Hyperium.CONFIG.getConfig()));
        } catch (Exception e) {
            builder.append("Config: Error");
        }
    }

    private static void tryKeybinds(StringBuilder builder) {
        try {
            builder.append("Keybinds: ").append(printer.toJson(Hyperium.INSTANCE.getHandlers().getKeybindHandler().getKeyBindConfig().getKeyBindJson().getData()));
        } catch (Exception e) {
            builder.append("Keybinds: Error");
        }
    }

    private static void tryLevelhead(StringBuilder builder) {
        try {
            builder.append("Count: ").append(Hyperium.INSTANCE.getModIntegration().getLevelhead().count).append("\n");
            builder.append("Wait: ").append(Hyperium.INSTANCE.getModIntegration().getLevelhead().wait).append("\n");
            builder.append("Hypixel: ").append(HypixelDetector.getInstance().isHypixel()).append("\n");
            builder.append("Local Stats: ").append(HypixelDetector.getInstance().isHypixel()).append("\n");
            builder.append("Header State: ").append(Hyperium.INSTANCE.getModIntegration().getLevelhead().getHeaderConfig()).append("\n");
            builder.append("Footer State: ").append(Hyperium.INSTANCE.getModIntegration().getLevelhead().getFooterConfig()).append("\n");
        } catch (Exception e) {
            builder.append("Levelhead: Error");
        }
    }

    public static String get() {
        StringBuilder builder = new StringBuilder();
        PurchaseApi api = PurchaseApi.getInstance();
        if (api == null) return "";

        HyperiumPurchase self = PurchaseApi.getInstance().getSelf();
        builder.append("\n");
        builder.append("Purchase callback: ");
        if (self != null) {
            JsonHolder response = self.getResponse();
            if (response != null)
                builder.append(printer.toJson(response.getObject()));
        }
        builder.append("\n");
        builder.append("\n");
        HypixelDetector instance = HypixelDetector.getInstance();
        if (instance != null) builder.append("Hypixel: ").append(instance.isHypixel());
        builder.append("\n");
        builder.append("\n");
        builder.append("\n\n");
        NetworkHandler networkHandler = Hyperium.INSTANCE.getNetworkHandler();
        if (networkHandler != null) {
            List<String> verboseLogs = networkHandler.getVerboseLogs();
            for (String verboseLog : verboseLogs) {
                builder.append(verboseLog);
                builder.append("\n");
            }
            builder.append(verboseLogs);
            builder.append("\n");
        }
        tryConfig(builder);
        builder.append("\n");
        builder.append("\n");
        tryChromaHUD(builder);
        builder.append("\n");
        builder.append("\n");
        tryKeybinds(builder);
        builder.append("\n");
        builder.append("\n");
        tryLevelhead(builder);
        builder.append("\n");
        builder.append("\n");
        builder.append("Levelhead");
        builder.append("\n");
        return builder.toString();
    }

    @Override
    public String getName() {
        return "hyperium_debug";
    }

    @Override
    public String getUsage() {
        return "Usage: /hyperium_debug";
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("log")) {
            Hyperium.INSTANCE.getNetworkHandler().setLog(true);
            GeneralChatHandler.instance().sendMessage("Enabled logging, please restart your game to begin. It will be auto disabled after next launch.");
        }
    }
}
