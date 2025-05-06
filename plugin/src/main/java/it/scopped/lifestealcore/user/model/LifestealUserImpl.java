package it.scopped.lifestealcore.user.model;

import it.scopped.lifestealcore.user.statistics.UserStatistics;
import it.scopped.lifestealcore.user.statistics.UserStatisticsImpl;

import java.util.UUID;

public class LifestealUserImpl implements LifestealUser {

    private final UUID uuid;
    private final String name;
    private final UserStatistics statistics;

    private long combatTime;

    public LifestealUserImpl(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.statistics = new UserStatisticsImpl();
        this.combatTime = 0;
    }

    @Override
    public UUID uuid() {
        return uuid;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public UserStatistics statistics() {
        return statistics;
    }

    @Override
    public long combatTime() {
        return combatTime;
    }

    @Override
    public void updateCombatTime(long combatTime) {
        this.combatTime = combatTime;
    }
}
