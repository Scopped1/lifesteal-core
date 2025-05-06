package it.scopped.lifestealcore;

import it.scopped.lifestealcore.configurations.MessagesConfig;
import it.scopped.lifestealcore.configurations.SettingsConfig;
import it.scopped.lifestealcore.leaderboard.LeaderboardService;
import it.scopped.lifestealcore.providers.logger.LoggerProvider;
import it.scopped.lifestealcore.providers.messages.MessagesProvider;
import it.scopped.lifestealcore.providers.scheduler.SchedulerProvider;
import it.scopped.lifestealcore.user.UserService;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ExecutorService;

public interface LifestealCoreAPI {

    void disable();

    MessagesConfig messagesConfig();

    SettingsConfig settingsConfig();

    UserService userService();

    LeaderboardService leaderboardService();

    MessagesProvider messagesProvider();

    LoggerProvider loggerProvider();

    SchedulerProvider schedulerProvider();

    Server server();

    Plugin bootstrap();

    ExecutorService executor();

}
