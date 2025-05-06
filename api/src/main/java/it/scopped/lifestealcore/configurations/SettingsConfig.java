package it.scopped.lifestealcore.configurations;

import it.scopped.lifestealcore.LifestealCoreAPI;
import net.pino.simpleconfig.BaseConfig;
import net.pino.simpleconfig.annotations.Config;
import net.pino.simpleconfig.annotations.ConfigFile;
import net.pino.simpleconfig.annotations.inside.Path;
import org.bukkit.plugin.Plugin;

@Config
@ConfigFile("settings.yml")
public class SettingsConfig extends BaseConfig {

    public SettingsConfig(LifestealCoreAPI plugin) {
        registerConfig(plugin.bootstrap());
    }

    @Path("database.host")
    public final String databaseHost = "localhost";

    @Path("database.port")
    public final int databasePort = 3306;

    @Path("database.database")
    public final String databaseDatabase = "lifesteal";

    @Path("database.username")
    public final String databaseUsername = "root";

    @Path("database.password")
    public final String databasePassword = "";

}
