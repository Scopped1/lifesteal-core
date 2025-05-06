package it.scopped.lifestealcore.leaderboard;

import it.scopped.lifestealcore.user.statistics.StatisticType;

import java.util.List;
import java.util.Map;

public interface LeaderboardService {

    void refreshLeaderboards();

    List<TopPlayer> leaderboards(StatisticType statisticType);

    void flush();

}
