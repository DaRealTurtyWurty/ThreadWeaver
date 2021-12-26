package io.github.darealturtywurty.threadweaver.util;

import java.util.logging.Level;

import com.typesafe.config.ConfigException;

import io.github.darealturtywurty.threadweaver.data.GuildInfo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

public class DiscordUtils {

    public static Role getModeratorRole(final Guild guild) {
        var roleID = 0L;
        try {
            roleID = getModRoleFromGuild(guild);
        } catch (final ConfigException.Missing e) {
            Constants.LOGGER.log(Level.INFO, "No Moderation Role found in config for guild: {0} [{1}]!",
                    new Object[] { guild.getName(), guild.getId() });
        }

        if (roleID <= 0) {
            final var modRole = guild.getRolesByName("moderator", true).get(0);
            if (modRole == null) {
                Constants.LOGGER.log(Level.WARNING, "No Moderation Role found in guild: {0} [{1}]!",
                        new Object[] { guild.getName(), guild.getId() });
                return null;
            }
            roleID = modRole.getIdLong();
        }

        setModRoleForGuild(guild, roleID);

        final var moderatorRole = guild.getRoleById(roleID);
        if (moderatorRole == null) {
            Constants.LOGGER.log(Level.WARNING, "No Moderation Role found in guild: {0} [{1}]!",
                    new Object[] { guild.getName(), guild.getId() });
        }
        return moderatorRole;
    }

    public static long getModRoleFromGuild(final Guild guild) {
        return Constants.GUILDS.get(guild.getIdLong()).modRoleID;
    }

    public static boolean isBotOwner(final User user) {
        return ConfigUtils.getOwnerID() == user.getIdLong();
    }

    public static boolean isModerator(final Guild guild, final Member member) {
        if (member == null)
            return false;

        if (!member.isOwner()) {
            final var modRole = getModeratorRole(guild);
            return modRole != null && member.getRoles().contains(modRole);
        }
        return true;
    }

    public static boolean isModerator(final Guild guild, final User user) {
        final var member = guild.getMember(user);
        if (member == null) {
            Constants.LOGGER.log(Level.WARNING, "Cannot find user: {0} [{1}] in guild: {2} [{3}]!",
                    new Object[] { user.getName() + "#" + user.getDiscriminator(), user.getId(),
                            guild.getName(), guild.getId() });
            return false;
        }

        if (!member.isOwner()) {
            final var modRole = getModeratorRole(guild);
            return modRole != null && member.getRoles().contains(modRole);
        }
        return true;
    }

    public static void setModRoleForGuild(final Guild guild, final long roleID) {
        final GuildInfo info = Constants.GUILDS.get(guild.getIdLong()) == null ? new GuildInfo(guild)
                : Constants.GUILDS.get(guild.getIdLong());
        info.modRoleID = roleID;
        Constants.GUILDS.put(guild.getIdLong(), info);
        InfoUtils.writeGuildInfo();
    }
}
