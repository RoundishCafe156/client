package me.semx11.autotip.command.impl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import me.semx11.autotip.Autotip;
import me.semx11.autotip.chat.MessageOption;
import me.semx11.autotip.chat.MessageUtil;
import me.semx11.autotip.command.CommandAbstract;
import me.semx11.autotip.config.Config;
import me.semx11.autotip.config.GlobalSettings;
import me.semx11.autotip.core.SessionManager;
import me.semx11.autotip.core.TaskManager;
import me.semx11.autotip.core.TaskManager.TaskType;
import me.semx11.autotip.event.impl.EventClientConnection;
import me.semx11.autotip.universal.UniversalUtil;
import static net.minecraft.command.CommandBase.getListOfStringsMatchingLastWord;

public class CommandAutotip extends CommandAbstract {
    private static final DateTimeFormatter WAVE_FORMAT = DateTimeFormatter.ofPattern("mm:ss");

    public CommandAutotip(Autotip autotip) {
        super(autotip);
    }

    @Override
    public String getName() {
        return "autotip";
    }

    @Override
    public String getUsage() {
        return autotip.getLocaleHolder().getKey("command.usage");
    }


    @Override
    public List<String> getCommandAliases() {
        return Collections.emptyList();
    }

    @Override
    public void onExecute(String[] args) {
        Config config = autotip.getConfig();
        MessageUtil messageUtil = autotip.getMessageUtil();
        TaskManager taskManager = autotip.getTaskManager();
        SessionManager manager = autotip.getSessionManager();
        GlobalSettings settings = autotip.getGlobalSettings();

        if (args.length <= 0) {
            messageUtil.sendKey("command.usage");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "m":
            case "messages":
                try {
                    if (args.length > 1) {
                        MessageOption option = MessageOption.valueOfIgnoreCase(args[1]);
                        config.setMessageOption(option).save();
                    } else {
                        config.nextMessageOption().save();
                    }
                    messageUtil.sendKey("command.messages.next", config.getMessageOption());
                } catch (IllegalArgumentException e) {
                    messageUtil.sendKey("command.messages.error", args.length > 1 ? args[1] : null);
                }
                break;
            case "t":
            case "toggle":
                if (!manager.isOnHypixel()) {
                    config.toggleEnabled().save();
                    messageUtil.getKeyHelper("command.toggle")
                            .sendKey(config.isEnabled() ? "enabled" : "disabled");
                    return;
                }
                if (!config.isEnabled()) {
                    if (!manager.isLoggedIn()) {
                        taskManager.executeTask(TaskType.LOGIN, manager::login);
                        config.setEnabled(true).save();
                        messageUtil.sendKey("command.toggle.enabled");
                    } else {
                        messageUtil.sendKey("command.toggle.error");
                    }
                } else {
                    if (manager.isLoggedIn()) {
                        taskManager.executeTask(TaskType.LOGOUT, manager::logout);
                        config.setEnabled(false).save();
                        messageUtil.sendKey("command.toggle.disabled");
                    } else {
                        messageUtil.sendKey("command.toggle.error");
                    }
                }
                break;
            case "w":
            case "wave":
                if (!config.isEnabled()) {
                    messageUtil.sendKey("error.disabled");
                    return;
                }
                if (!manager.isOnHypixel()) {
                    messageUtil.sendKey("error.disabledHypixel");
                    return;
                }
                if (manager.getNextTipWave() == 0) {
                    messageUtil.sendKey("command.wave.error");
                    return;
                }

                long t = System.currentTimeMillis();
                String next = LocalTime.MIN.plusSeconds((manager.getNextTipWave() - t) / 1000 + 1)
                        .format(WAVE_FORMAT);
                String last = LocalTime.MIN.plusSeconds((t - manager.getLastTipWave()) / 1000)
                        .format(WAVE_FORMAT);

                messageUtil.getKeyHelper("command.wave").separator()
                        .sendKey("nextWave", next)
                        .sendKey("lastWave", last).separator();
                break;
            case "debug":
                EventClientConnection event = autotip.getEvent(EventClientConnection.class);
                Object header = event.getHeader();
                messageUtil.getKeyHelper("command.debug").separator()
                        .sendKey("serverIp", event.getServerIp())
                        .sendKey("mcVersion", autotip.getMcVersion())
                        .sendKey("header." + (header == null ? "none" : "present"),
                                UniversalUtil.getUnformattedText(header)).separator();
                break;
            case "reload":
                try {
                    autotip.reloadGlobalSettings();
                    autotip.reloadLocale();
                    messageUtil.sendKey("command.reload.success");
                } catch (IllegalStateException e) {
                    messageUtil.sendKey("command.reload.error");
                }
                break;
            default:
                messageUtil.send(getUsage());
                break;
        }
    }

    @Override
    public List<String> onTabComplete(String[] args) {
        return getListOfStringsMatchingLastWord(args, "messages", "toggle", "wave");
    }
}
