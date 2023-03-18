package com.martin.welcomess;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

public final class WelcomeMessages extends JavaPlugin implements Listener {
    private final Random random = new Random();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("WelcomeMessages plugin enabled!");
        getServer().getPluginManager().registerEvents(this, this);

        saveDefaultConfig();
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
            List<String> messages = getConfig().getStringList("returning_player_messages");
            message = messages.get(random.nextInt(messages.size())).replace("{player}", event.getPlayer().getName());
        } else {
            List<String> messages = getConfig().getStringList("new_player_messages");
            message = messages.get(random.nextInt(messages.size())).replace("{player}", event.getPlayer().getName());
        }

        // displays a personalized welcome message if one is found
        if (!message.isEmpty()) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        } else {
            getLogger().info("No messages in config.yml");
        }
    }
}
