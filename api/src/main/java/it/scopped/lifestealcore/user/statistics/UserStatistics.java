package it.scopped.lifestealcore.user.statistics;

import java.util.Map;

public interface UserStatistics {

    Map<StatisticType, Long> loadedStatistics();

    long kills();

    long deaths();

    long hearts();

    long playTime();

    void statistic(StatisticType statistic, long amount);

    void addStatistic(StatisticType statistic, long amount);

    void removeStatistic(StatisticType statistic, long amount);

    default long value(StatisticType statisticType) {
        return loadedStatistics().get(statisticType);
    }

}