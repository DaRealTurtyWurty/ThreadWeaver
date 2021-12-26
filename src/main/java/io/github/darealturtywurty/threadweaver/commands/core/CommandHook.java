package io.github.darealturtywurty.threadweaver.commands.core;

import io.github.darealturtywurty.threadweaver.util.DiscordUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class CommandHook extends ListenerAdapter {

    protected final CommandManager manager = new CommandManager();

    @Override
    public void onGuildReady(final GuildReadyEvent event) {
        final Guild guild = event.getGuild();

        final CommandListUpdateAction updates = guild.updateCommands();
        updates.addCommands(this.manager.commands.stream()
                .filter(cmd -> cmd.productionReady() || !DiscordUtils.notTestServer(guild)).map(cmd -> {
                    final CommandData data = new CommandData(cmd.getName(), cmd.getDescription());
                    if (!cmd.getSubcommandGroupData().isEmpty()) {
                        data.addSubcommandGroups(cmd.getSubcommandGroupData());
                    } else if (!cmd.getSubcommandData().isEmpty()) {
                        data.addSubcommands(cmd.getSubcommandData());
                    } else if (!cmd.getOptions().isEmpty()) {
                        data.addOptions(cmd.getOptions());
                    }

                    return data;
                }).toList());
        updates.queue();
    }

    @Override
    public void onSlashCommand(final SlashCommandEvent event) {
        if (event.getUser().isBot() || event.getUser().isSystem())
            return;

        this.manager.handle(event);
    }
}