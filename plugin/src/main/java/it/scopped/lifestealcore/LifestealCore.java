package it.scopped.lifestealcore;

import it.scopped.lifestealcore.boot.LifestealCoreBootstrap;
import it.scopped.lifestealcore.commands.LifestealCommand;
import it.scopped.lifestealcore.configurations.MessagesConfig;
import it.scopped.lifestealcore.configurations.SettingsConfig;
import it.scopped.lifestealcore.database.DatabaseService;
import it.scopped.lifestealcore.hooks.PlaceholderHook;
import it.scopped.lifestealcore.leaderboard.LeaderboardService;
import it.scopped.lifestealcore.leaderboard.LeaderboardServiceImpl;
import it.scopped.lifestealcore.listeners.PlayerListeners;
import it.scopped.lifestealcore.providers.logger.LoggerProvider;
import it.scopped.lifestealcore.providers.logger.LoggerProviderImpl;
import it.scopped.lifestealcore.providers.messages.MessagesProvider;
import it.scopped.lifestealcore.providers.messages.MessagesProviderImpl;
import it.scopped.lifestealcore.providers.scheduler.SchedulerProvider;
import it.scopped.lifestealcore.providers.scheduler.SchedulerProviderImpl;
import it.scopped.lifestealcore.tasks.PlayerSaverTask;
import it.scopped.lifestealcore.user.UserService;
import it.scopped.lifestealcore.user.UserServiceImpl;
import it.scopped.lifestealcore.user.listeners.AccessListeners;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LifestealCore implements LifestealCoreAPI {

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    private final LifestealCoreBootstrap bootstrap;

    private final SettingsConfig settingsConfig;
    private final MessagesConfig messagesConfig;

    private final Lamp<BukkitCommandActor> lamp;

    private final LoggerProvider loggerProvider;
    private final MessagesProvider messagesProvider;
    private final SchedulerProvider schedulerProvider;

    private final DatabaseService databaseService;
    private final UserService userService;
    private final LeaderboardService leaderboardService;

    public LifestealCore(LifestealCoreBootstrap bootstrap) {
        this.bootstrap = bootstrap;

        this.settingsConfig = new SettingsConfig(this);
        this.messagesConfig = new MessagesConfig(this);

        this.lamp = BukkitLamp.builder(bootstrap).build();

        this.loggerProvider = new LoggerProviderImpl();
        this.messagesProvider = new MessagesProviderImpl(this);
        this.schedulerProvider = new SchedulerProviderImpl(this);

        this.databaseService = new DatabaseService(this);
        this.userService = new UserServiceImpl();
        this.leaderboardService = new LeaderboardServiceImpl(this);

        new PlayerSaverTask(this).register();

        commands(
                new LifestealCommand(this)
        );

        listeners(
                new PlayerListeners(this),
                new AccessListeners(this)
        );

        new PlaceholderHook(this).register();
    }

    @Override
    public void disable() {
        executor.shutdown();

        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                loggerProvider.warn("Executor did not terminate in time.");
            }
        } catch (InterruptedException e) {
            loggerProvider.error("Executor shutdown interrupted.", e);
            Thread.currentThread().interrupt();
        }
    }

    private void commands(Object... commands) {
        for (Object command : commands) {
            lamp.register(command);
        }
    }

    private void listeners(Listener... listeners) {
        for (Listener listener : listeners) {
            bootstrap.getServer().getPluginManager().registerEvents(listener, bootstrap);
        }
    }

    @Override
    public MessagesConfig messagesConfig() {
        return messagesConfig;
    }

    @Override
    public SettingsConfig settingsConfig() {
        return settingsConfig;
    }

    public DatabaseService databaseService() {
        return databaseService;
    }

    @Override
    public UserService userService() {
        return userService;
    }

    @Override
    public LeaderboardService leaderboardService() {
        return leaderboardService;
    }

    @Override
    public MessagesProvider messagesProvider() {
        return messagesProvider;
    }

    @Override
    public LoggerProvider loggerProvider() {
        return loggerProvider;
    }

    @Override
    public SchedulerProvider schedulerProvider() {
        return schedulerProvider;
    }

    @Override
    public Server server() {
        return bootstrap.getServer();
    }

    @Override
    public Plugin bootstrap() {
        return bootstrap;
    }

    @Override
    public ExecutorService executor() {
        return executor;
    }
}
