package com.martin.welcomess;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Random;

public final class WelcomeMessages extends JavaPlugin implements Listener {

    private FileConfiguration config;
    private final Random random = new Random();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("WelcomeMessages plugin enabled!");
        getServer().getPluginManager().registerEvents(this, this);

        // Load the config file
        loadConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("WelcomeMessages plugin disabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // disables vanilla messages
        event.joinMessage(null);
      
        String message;

        if (event.getPlayer().hasPlayedBefore()) {
            List<String> messages = config.getStringList("returning_player_messages");
            message = messages.get(random.nextInt(messages.size())).replace("{player}", event.getPlayer().getName());
        } else {
            List<String> messages = config.getStringList("new_player_messages");
            message = messages.get(random.nextInt(messages.size())).replace("{player}", event.getPlayer().getName());
        }

        // displays a personalized welcome message if one is found
        if (!message.isEmpty()) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        } else {
            getLogger().info("No messages in config.yml");
        }
    }

    private void loadConfig() {
        // Creating the configuration file if it doesn't exist
        if (!getDataFolder().exists()) {
            if (getDataFolder().mkdir()) {
                getLogger().info("Config created!");
            }
        }

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            saveResource("config.yml", false);
        }

        // Load the config file
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
    }
}
