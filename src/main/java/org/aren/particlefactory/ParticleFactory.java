package org.aren.particlefactory;

import dev.jorel.commandapi.AbstractCommandAPICommand;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.aren.particlefactory.command.ParticleCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class ParticleFactory extends JavaPlugin {

    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void onLoad() {
        plugin = this;
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
        ParticleCommand.commands().forEach(AbstractCommandAPICommand::register);
        ParticleManager manager = ParticleManager.initialize(plugin);


    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        CommandAPI.onEnable();
        plugin = this;

        ParticleScheduler scheduler = ParticleManager.getInstance().getScheduler();
        scheduler.runTaskTimer(plugin, 0, 1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        CommandAPI.onDisable();
    }
}
