package org.aren.particlefactory;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConfigFile {

    private File file;
    private FileConfiguration configuration;


    public ConfigFile(String name, String path) {
        file = new File(name + ".yml", path);

        try {
            file.mkdir();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }
}
