package it.scopped.lifestealcore.user.listeners;

import it.scopped.lifestealcore.LifestealCore;
import it.scopped.lifestealcore.user.model.LifestealUser;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

public class AccessListeners implements Listener {

    private final LifestealCore plugin;

    public AccessListeners(LifestealCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerPreLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        Optional<LifestealUser> user = plugin.userService().getOrCreate(player);

        if (user.isEmpty()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Data not found."));
            return;
        }

        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            plugin.userService().unload(event.getPlayer());
        }
    }
}
