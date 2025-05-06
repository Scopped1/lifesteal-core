package it.scopped.lifestealcore.hooks;

import it.scopped.lifestealcore.LifestealCore;
import it.scopped.lifestealcore.leaderboard.TopPlayer;
import it.scopped.lifestealcore.user.model.LifestealUser;
import it.scopped.lifestealcore.user.statistics.StatisticType;
import it.scopped.lifestealcore.user.statistics.UserStatistics;
import it.scopped.lifestealcore.utility.StringsUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class PlaceholderHook extends PlaceholderExpansion {

    private final LifestealCore plugin;

    public PlaceholderHook(LifestealCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.startsWith("leaderboard_")) {
            return handleLeaderboard(params);
        }

        Optional<LifestealUser> user = plugin.userService().find(player);
        if (user.isEmpty()) return "N/A";

        UserStatistics statistics = user.get().statistics();

        return switch (params) {
            case "kills" -> String.valueOf(statistics.kills());
            case "deaths" -> String.valueOf(statistics.deaths());
            case "hearts" -> String.valueOf(statistics.hearts());
            case "play_time" -> StringsUtil.parseStringTime(statistics.playTime());
            default -> "N/A";
        };
    }

    private String handleLeaderboard(String params) {
        String[] parts = params.split("_");
        if (parts.length != 4) return "N/A";

        try {
            StatisticType type = StatisticType.valueOf(parts[1].toUpperCase());
            int rank = Integer.parseInt(parts[2]) - 1;
            boolean name = parts[3].equalsIgnoreCase("name");

            List<TopPlayer> leaderboard = plugin.leaderboardService().leaderboards(type);
            if (rank < 0 || rank >= leaderboard.size()) {
                return name ? "---" : "0";
            }

            TopPlayer top = leaderboard.get(rank);
            return name ? top.name() : String.valueOf(top.value());

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            plugin.loggerProvider().error("Failed to handle leaderboard placeholder. Error: " + e.getMessage());
            return "N/A";
        }
    }

    @Override
    public @NotNull String getIdentifier() {
        return "lifesteal";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Scopped_";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }
}