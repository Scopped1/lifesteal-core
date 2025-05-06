package it.scopped.lifestealcore.leaderboard;

import it.scopped.lifestealcore.LifestealCore;
import it.scopped.lifestealcore.user.statistics.StatisticType;
import it.scopped.lifestealcore.user.statistics.UserStatistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaderboardServiceImpl implements LeaderboardService {

    private final LifestealCore plugin;
    private final Map<StatisticType, List<TopPlayer>> loadedLeaderboards = new HashMap<>();

    public LeaderboardServiceImpl(LifestealCore plugin) {
        this.plugin = plugin;
        refreshLeaderboards();
    }

    @Override
    public void refreshLeaderboards() {
        loadedLeaderboards.clear();

        for (StatisticType statisticType : StatisticType.values()) {

            List<TopPlayer> leaderboard = plugin.userService().loadedPlayers().values().stream()
                    .filter(player -> player.statistics().loadedStatistics().containsKey(statisticType))
                    .sorted((user1, user2) -> {
                        UserStatistics statistics1 = user1.statistics();
                        UserStatistics statistics2 = user2.statistics();

                        return Long.compare(statistics1.value(statisticType), statistics2.value(statisticType));
                    })
                    .limit(10)
                    .map(user -> new TopPlayer(user.name(), user.statistics().value(statisticType)))
                    .collect(Collectors.toList());

            loadedLeaderboards.put(statisticType, leaderboard);
        }
    }

    @Override
    public List<TopPlayer> leaderboards(StatisticType statisticType) {
        return loadedLeaderboards.getOrDefault(statisticType, new ArrayList<>());
    }

    @Override
    public void flush() {
        loadedLeaderboards.clear();
    }
}
