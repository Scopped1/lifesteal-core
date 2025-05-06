package it.scopped.lifestealcore.user;

import it.scopped.lifestealcore.user.model.LifestealUser;
import it.scopped.lifestealcore.user.model.LifestealUserImpl;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserServiceImpl implements UserService {

    private final ConcurrentMap<UUID, LifestealUser> loadedPlayers = new ConcurrentHashMap<>();

    @Override
    public Optional<LifestealUser> getOrCreate(Player player) {
        return Optional.of(loadedPlayers.computeIfAbsent(player.getUniqueId(),
                uuid -> new LifestealUserImpl(uuid, player.getName())));
    }

    @Override
    public void unload(Player player) {
        loadedPlayers.remove(player.getUniqueId());
    }

    @Override
    public Optional<LifestealUser> find(Player player) {
        return Optional.ofNullable(loadedPlayers.get(player.getUniqueId()));
    }

    @Override
    public void flush() {
        loadedPlayers.clear();
    }

    @Override
    public ConcurrentMap<UUID, LifestealUser> loadedPlayers() {
        return loadedPlayers; //TODO: to review
    }
}
