package io.github.darealturtywurty.threadweaver.data;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import io.github.darealturtywurty.threadweaver.commands.core.GuildCommand;
import net.dv8tion.jda.api.entities.Guild;

public class GuildInfo {
    public final Guild guild;
    public final Map<Long, Map<GuildCommand, Instant>> userCooldowns = new HashMap<>();
    public String prefix = "!";
    public long modRoleID;

    public GuildInfo(Guild guild) {
        this.guild = guild;
    }
}
