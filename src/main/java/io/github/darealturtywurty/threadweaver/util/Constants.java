package io.github.darealturtywurty.threadweaver.util;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.darealturtywurty.threadweaver.data.GuildInfo;

public class Constants {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().setLenient()
            .create();
    protected static final Path CONFIG_PATH = Path.of("botConfig.json");
    public static final Logger LOGGER = Logger.getGlobal();
    public static final Map<Long, GuildInfo> GUILDS = new HashMap<>();
}
