package cc.hyperium.handlers.handlers.chat;

import net.minecraft.util.IChatComponent;

public class KillTrackerChatHandler extends HyperiumChatHandler {

    public KillTrackerChatHandler() {}

    @Override
    public boolean chatReceived(IChatComponent component, String text) {
        return false;
    }
}
