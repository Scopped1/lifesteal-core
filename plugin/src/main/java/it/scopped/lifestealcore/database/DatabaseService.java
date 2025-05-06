package it.scopped.lifestealcore.database;

import it.scopped.lifestealcore.LifestealCore;
import it.scopped.lifestealcore.database.connector.ConnectionPoolManager;
import it.scopped.lifestealcore.database.connector.credentials.AuthCredentials;
import it.scopped.lifestealcore.database.query.DatabaseQuery;
import it.scopped.lifestealcore.user.model.LifestealUser;
import it.scopped.lifestealcore.user.model.LifestealUserImpl;
import it.scopped.lifestealcore.user.statistics.StatisticType;
import it.scopped.lifestealcore.user.statistics.UserStatistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class DatabaseService implements DatabaseQuery {

    private final LifestealCore plugin;
    private final ConnectionPoolManager connectionPoolManager;

    public DatabaseService(LifestealCore plugin) {
        this.plugin = plugin;
        this.connectionPoolManager = new ConnectionPoolManager(
                new AuthCredentials(
                        plugin.settingsConfig().databaseHost,
                        plugin.settingsConfig().databasePort,
                        plugin.settingsConfig().databaseDatabase,
                        plugin.settingsConfig().databaseUsername,
                        plugin.settingsConfig().databasePassword
                )
        );
    }

    public CompletableFuture<List<LifestealUser>> players() {
        return CompletableFuture.supplyAsync(() -> {
            List<LifestealUser> loadedPlayers = new ArrayList<>();

            try (Connection connection = connectionPoolManager.connection();
                 PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_PLAYERS);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    String name = resultSet.getString("name");

                    long kills = resultSet.getLong("kills");
                    long deaths = resultSet.getLong("deaths");
                    int hearts = resultSet.getInt("hearts");
                    long playTime = resultSet.getLong("play_time");

                    LifestealUser user = new LifestealUserImpl(UUID.fromString(uuid), name);
                    UserStatistics statistics = user.statistics();

                    statistics.statistic(StatisticType.KILLS, kills);
                    statistics.statistic(StatisticType.DEATHS, deaths);
                    statistics.statistic(StatisticType.HEARTS, hearts);
                    statistics.statistic(StatisticType.PLAY_TIME, playTime);

                    loadedPlayers.add(user);
                }

                return loadedPlayers;
            } catch (SQLException e) {
                plugin.loggerProvider().error("Failed to load players.", e);
            }

            return loadedPlayers;
        }, plugin.executor());
    }

    public CompletableFuture<Void> save(Collection<LifestealUser> players) {
        return CompletableFuture.runAsync(() -> {

            try (Connection connection = connectionPoolManager.connection();
                 PreparedStatement statement = connection.prepareStatement(DatabaseQuery.SAVE_PLAYER)) {

                for (LifestealUser user : players) {
                    statement.setString(1, user.uuid().toString());
                    statement.setString(2, user.name());

                    UserStatistics statistics = user.statistics();

                    statement.setLong(3, statistics.kills());
                    statement.setLong(4, statistics.deaths());
                    statement.setLong(5, statistics.hearts());
                    statement.setLong(6, statistics.playTime());
                    statement.addBatch();
                }

                statement.executeBatch();
            } catch (SQLException e) {
                plugin.loggerProvider().error("Failed to save players.", e);
            }
        }, plugin.executor());
    }

    public void deletePlayer(UUID uuid, String name) {
        try (Connection connection = connectionPoolManager.connection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAYER)) {

            statement.setString(1, uuid.toString());
            statement.setString(2, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            plugin.loggerProvider().error("Failed to delete player.", e);
        }
    }

}
