package it.scopped.lifestealcore.user;

import it.scopped.lifestealcore.user.model.LifestealUser;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public interface UserService {

    ConcurrentMap<UUID, LifestealUser> loadedPlayers();

    Optional<LifestealUser> getOrCreate(Player player);

    void unload(Player player);

    void flush();

    Optional<LifestealUser> find(Player player);

}
