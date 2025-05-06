package it.scopped.lifestealcore.tasks;

import it.scopped.lifestealcore.LifestealCore;
import it.scopped.lifestealcore.user.model.LifestealUser;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerSaverTask implements Runnable {

    private final LifestealCore plugin;

    public PlayerSaverTask(LifestealCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Collection<LifestealUser> players = plugin.userService().loadedPlayers().values();
        plugin.databaseService().save(players);
    }

    public void register() {
        plugin.schedulerProvider().timer(this, 0, 3, TimeUnit.MINUTES);
    }
}
