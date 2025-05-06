package it.scopped.lifestealcore.user.model;

import it.scopped.lifestealcore.user.statistics.UserStatistics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface LifestealUser {

    UUID uuid();

    String name();

    UserStatistics statistics();

    long combatTime();

    void updateCombatTime(long combatTime);

    default Optional<Player> bukkitPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(uuid()));
    }

}
