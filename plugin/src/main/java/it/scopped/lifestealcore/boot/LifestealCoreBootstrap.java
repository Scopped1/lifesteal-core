package it.scopped.lifestealcore.boot;

import it.scopped.lifestealcore.LifestealCore;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class LifestealCoreBootstrap extends JavaPlugin {

    private LifestealCore plugin;

    @Override
    public void onEnable() {
        try {
            this.plugin = new LifestealCore(this);
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to enable the plugin!", e);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if (this.plugin != null) this.plugin.disable();
    }
}
