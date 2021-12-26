package io.github.darealturtywurty.threadweaver.util;

import java.io.IOException;
import java.nio.file.Files;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.github.darealturtywurty.threadweaver.data.GuildInfo;
import net.dv8tion.jda.api.entities.Guild;

public class InfoUtils {

    public static void readGuildInfo(final Guild guild) {
        try {
            if (!Files.exists(Constants.GUILD_INFO_PATH))
                return;
            final JsonArray json = Constants.GSON.fromJson(Files.readString(Constants.GUILD_INFO_PATH),
                    JsonArray.class);
            for (var guildIndex = 0; guildIndex < json.size(); guildIndex++) {
                final var guildObject = json.get(guildIndex).getAsJsonObject();
                final var info = new GuildInfo(guild);
                info.prefix = getOrDefaultJson(guildObject, "Prefix", "!").getAsString();
                info.modRoleID = getOrDefaultJson(guildObject, "ModeratorRoleID", 621370936112971777L)
                        .getAsLong();
                Constants.GUILDS.put(guild.getIdLong(), info);
            }
        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void writeGuildInfo() {
        try {
            if (!Files.exists(Constants.GUILD_INFO_PATH)) {
                Files.createFile(Constants.GUILD_INFO_PATH);
            }
            final var json = new JsonArray();
            Constants.GUILDS.forEach((guild, info) -> {
                final var newGuildInfo = Constants.GUILDS.get(guild);
                final var guildObject = new JsonObject();
                guildObject.addProperty("GuildID", guild);
                guildObject.addProperty("Prefix", newGuildInfo.prefix);
                guildObject.addProperty("ModeratorRoleID", newGuildInfo.modRoleID);

                json.add(guildObject);
            });
            Files.writeString(Constants.GUILD_INFO_PATH, Constants.GSON.toJson(json));
        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static JsonElement getOrDefaultJson(final JsonObject object, final String name,
            final Boolean defaultValue) {
        JsonElement obj = object.get(name);
        if (obj == null) {
            object.addProperty(name, defaultValue);
            obj = object.get(name);
        }
        return obj;
    }

    private static JsonElement getOrDefaultJson(final JsonObject object, final String name,
            final Character defaultValue) {
        JsonElement obj = object.get(name);
        if (obj == null) {
            object.addProperty(name, defaultValue);
            obj = object.get(name);
        }
        return obj;
    }

    private static JsonElement getOrDefaultJson(final JsonObject object, final String name,
            final JsonElement defaultValue) {
        JsonElement obj = object.get(name);
        if (obj == null) {
            object.add(name, defaultValue);
            obj = object.get(name);
        }
        return obj;
    }

    private static JsonElement getOrDefaultJson(final JsonObject object, final String name,
            final Number defaultValue) {
        JsonElement obj = object.get(name);
        if (obj == null) {
            object.addProperty(name, defaultValue);
            obj = object.get(name);
        }

        return obj;
    }

    private static JsonElement getOrDefaultJson(final JsonObject object, final String name,
            final String defaultValue) {
        JsonElement obj = object.get(name);
        if (obj == null) {
            object.addProperty(name, defaultValue);
            obj = object.get(name);
        }
        return obj;
    }
}
