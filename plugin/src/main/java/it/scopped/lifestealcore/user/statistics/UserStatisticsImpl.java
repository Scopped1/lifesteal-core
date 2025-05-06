package it.scopped.lifestealcore.user.statistics;

import java.util.HashMap;
import java.util.Map;

public class UserStatisticsImpl implements UserStatistics {

    private final Map<StatisticType, Long> statistics = new HashMap<>();

    public UserStatisticsImpl() {
        for (StatisticType statistic : StatisticType.values()) {
            statistics.put(statistic, 0L);
        }
    }

    @Override
    public Map<StatisticType, Long> loadedStatistics() {
        return statistics;
    }

    @Override
    public long kills() {
        return statistics.get(StatisticType.KILLS);
    }

    @Override
    public long deaths() {
        return statistics.get(StatisticType.DEATHS);
    }

    @Override
    public long hearts() {
        return statistics.get(StatisticType.HEARTS);
    }

    @Override
    public long playTime() {
        return statistics.get(StatisticType.PLAY_TIME);
    }

    @Override
    public void statistic(StatisticType statistic, long amount) {
        statistics.put(statistic, amount);
    }

    @Override
    public void addStatistic(StatisticType statistic, long amount) {
        statistics.put(statistic, statistics.get(statistic) + amount);
    }

    @Override
    public void removeStatistic(StatisticType statistic, long amount) {
        statistics.put(statistic, statistics.get(statistic) - amount);
    }
}
