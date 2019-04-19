package me.semx11.autotip.core;

import me.semx11.autotip.Autotip;
import me.semx11.autotip.config.Config;
import me.semx11.autotip.util.FileUtil;

public class MigrationManager {
    private final FileUtil fileUtil;
    private final Config config;

    public MigrationManager(Autotip autotip) {
        this.fileUtil = autotip.getFileUtil();
        this.config = autotip.getConfig();
    }

    public void migrateLegacyFiles() {
        if (fileUtil.exists("options.at")) config.migrate();
        if (fileUtil.exists("tipped.at")) fileUtil.delete("tipped.at");
        if (fileUtil.exists("upgrade-date.at")) fileUtil.delete("upgrade-date.at");
    }
}
