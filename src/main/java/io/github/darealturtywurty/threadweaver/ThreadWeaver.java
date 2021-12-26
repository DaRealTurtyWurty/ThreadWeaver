package io.github.darealturtywurty.threadweaver;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.github.darealturtywurty.threadweaver.util.ConfigUtils;
import io.github.darealturtywurty.threadweaver.util.Constants;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import okhttp3.OkHttpClient;

public class ThreadWeaver {
    private ThreadWeaver(final JDA bot) {
        Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);
        Locale.setDefault(Locale.UK);
        Constants.LOGGER.info("I have finished loading everything!");
    }

    public static void main(String[] args) {
        ThreadWeaver.create(getToken(args));
    }

    private static ThreadWeaver create(String token) {
        try {
            return new ThreadWeaver(JDABuilder.createDefault(token).build());
        } catch (final Exception exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    private static String getToken(String[] args) {
        if (args.length >= 2 && "-t".equals(args[0]))
            return args[1];
        return ConfigUtils.getBotToken();
    }
}
