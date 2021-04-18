package net.at13.lightsleep;

import net.at13.lightsleep.players.SleepingPlayers;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

public final class LightSleep extends JavaPlugin {

    private final SleepingPlayers sleepingPlayers = new SleepingPlayers();
    private static Plugin plugin;

    public static int percentNeedToSleep = 60;

    private final EventListener eventListener = new EventListener(sleepingPlayers);

    @Override
    public void onEnable() {

        plugin = this;

        this.getCommand("changepercent").setExecutor(new CommandRegister());

        getServer().getPluginManager().registerEvents(eventListener, this);

        System.out.println(ChatColor.BLUE + "[LightSleep]" + ChatColor.WHITE + " LightSleep launched successfully - created by AT13");
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
