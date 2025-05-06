package it.scopped.lifestealcore.listeners;

import it.scopped.lifestealcore.LifestealCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerListeners implements Listener {

    private final LifestealCore plugin;

    public PlayerListeners(LifestealCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void death(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        //TODO: ...
    }

}
