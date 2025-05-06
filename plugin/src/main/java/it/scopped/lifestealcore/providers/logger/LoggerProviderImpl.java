package it.scopped.lifestealcore.providers.logger;

import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerProviderImpl implements LoggerProvider {

    private static final Logger LOGGER = Bukkit.getLogger();

    @Override
    public void info(String message) {
        LOGGER.info(message);
    }

    @Override
    public void warn(String message) {
        LOGGER.warning(message);
    }

    @Override
    public void error(String message) {
        LOGGER.severe(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        LOGGER.log(Level.SEVERE, message, throwable);
    }
}
