package me.semx11.autotip.api.reply.impl;

import me.semx11.autotip.api.RequestType;
import me.semx11.autotip.api.reply.Reply;

public class SettingsReply extends Reply {
    public SettingsReply() {}

    public SettingsReply(boolean success) {
        super(success);
    }

    public GlobalSettings getSettings() {
        return settings;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.SETTINGS;
    }
}
