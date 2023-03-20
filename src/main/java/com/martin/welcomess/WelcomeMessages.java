package com.martin.welcomess;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
        // Get player from event
        Player player = event.getPlayer();

        // Check if player was already on the server
        if (player.hasPlayedBefore()) {
            event.joinMessage(null); // disables join message
            List<String> return_messages = getConfig().getStringList("returning_player_messages");
            String return_message = return_messages.get(random.nextInt(return_messages.size())).replace("{player}", player.getName());
            // Message to (and only for) the joining player
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', return_message));
        } else {
            List<String> new_messages = getConfig().getStringList("new_player_messages");
            String new_message = new_messages.get(random.nextInt(new_messages.size())).replace("{player}", player.getName());
            // Message to all online players
            event.joinMessage(Component.text(ChatColor.translateAlternateColorCodes('&', new_message)));
        }
    }
}
