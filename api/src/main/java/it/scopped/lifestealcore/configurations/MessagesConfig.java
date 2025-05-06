package it.scopped.lifestealcore.configurations;

import it.scopped.lifestealcore.LifestealCoreAPI;
import net.pino.simpleconfig.BaseConfig;
import net.pino.simpleconfig.annotations.Config;
import net.pino.simpleconfig.annotations.ConfigFile;

@Config
@ConfigFile("messages.yml")
public class MessagesConfig extends BaseConfig {

    public MessagesConfig(LifestealCoreAPI plugin) {
        registerConfig(plugin.bootstrap());
    }

}
