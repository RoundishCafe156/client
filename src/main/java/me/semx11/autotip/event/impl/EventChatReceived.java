package me.semx11.autotip.event.impl;

import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.ServerChatEvent;
import me.semx11.autotip.Autotip;
import me.semx11.autotip.command.impl.CommandLimbo;
import me.semx11.autotip.config.Config;
import me.semx11.autotip.event.Event;
import me.semx11.autotip.universal.UniversalUtil;

public class EventChatReceived implements Event {
    private final Autotip autotip;

    public EventChatReceived(Autotip autotip) {
        this.autotip = autotip;
    }

    @InvokeEvent
    public void onChat(ServerChatEvent event) {
        Config config = autotip.getConfig();

        if (!autotip.getSessionManager().isOnHypixel()) return;

        String msg = UniversalUtil.getUnformattedText(event);

        CommandLimbo limboCommand = autotip.getCommand(CommandLimbo.class);
        if (limboCommand.hasExecuted()) {
            if (msg.startsWith("A kick occurred in your connection")) {
                event.setCancelled(true);
            } else if (msg.startsWith("Illegal characters in chat")) {
                event.setCancelled(true);
                limboCommand.setExecuted(false);
            }
        }
    }
}
