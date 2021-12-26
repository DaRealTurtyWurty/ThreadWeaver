package io.github.darealturtywurty.threadweaver.util;

import java.io.IOException;
import java.nio.file.Files;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class ConfigUtils {

    @Nullable
    public static String getBotToken() {
        final JsonElement token = getConfig().get("token");
        return token == null || !token.isJsonPrimitive() ? null : token.getAsString();
    }

    @NotNull
    public static JsonObject getConfig() {
        try {
            return Constants.GSON.fromJson(Files.readString(Constants.CONFIG_PATH), JsonObject.class);
        } catch (JsonSyntaxException | IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    public static long getOwnerID() {
        final JsonElement ownerId = getConfig().get("ownerid");
        return ownerId == null || !ownerId.isJsonPrimitive() ? null : ownerId.getAsLong();
    }
}
