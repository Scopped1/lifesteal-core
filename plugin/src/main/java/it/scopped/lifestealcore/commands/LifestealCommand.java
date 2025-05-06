package it.scopped.lifestealcore.commands;

import it.scopped.lifestealcore.LifestealCore;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.CommandPlaceholder;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command("lifesteal")
@CommandPermission("lifesteal.command.admin")
public class LifestealCommand {

    private final LifestealCore plugin;

    public LifestealCommand(LifestealCore plugin) {
        this.plugin = plugin;
    }
}
