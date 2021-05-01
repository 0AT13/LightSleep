package net.at13.lightsleep;

import net.at13.lightsleep.players.SleepingPlayers;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.UUID;

public class EventListener implements Listener {

    private final SleepingPlayers sleepingPlayers;
    private World world;

    public EventListener(SleepingPlayers sleepingPlayer) {
        this.sleepingPlayers = sleepingPlayer;
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        world = player.getWorld();

        // java.util.Date not java.sql.Date
        long time = new Date().getTime();

        // Security check stuff
        sleepingPlayers.setSleepingState(playerUUID, time);

        // Get the number of players to check the required amount for sleep
        int playersCount = Bukkit.getOnlinePlayers().size();
        double sleepingPlayersPercent = (double)sleepingPlayers.Count() * 100 / playersCount;

        Bukkit.getScheduler().runTaskLater(LightSleep.getPlugin(), new Runnable() {

            public void run() {
                if(player.isSleeping()) {
                    Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + player.getName() + "" +
                            ChatColor.WHITE + " is sleeping");
                    Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + (int)sleepingPlayersPercent + "" + "(" + LightSleep.percentNeedToSleep + ")/100%" +
                            ChatColor.WHITE + " of players is sleeping");
                }
            }
        }, 10);

        Bukkit.getScheduler().runTaskLater(LightSleep.getPlugin(), () -> {

            // Make sure the player hasn't hopped out of bed
            if(player.isSleeping() && sleepingPlayers.time(playerUUID).equals(time) && (sleepingPlayersPercent >= LightSleep.percentNeedToSleep)) {

                // do task
                world.setTime(23900);
            }
            else { event.setCancelled(player.isSleeping()); }
        }, 100);
    }

    //Checking whenever player leave the bed and deleting him from the map of sleeping players
    @EventHandler
    public void onWokeUp(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + player.getName() + "" +
                ChatColor.WHITE + " woke up");

        sleepingPlayers.removeSleepingState(playerUUID);
    }

    //Checking whenever player leave the server and deleting him from the map of sleeping players
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if(sleepingPlayers.isSleeping(playerUUID) != null)
            sleepingPlayers.removeSleepingState(playerUUID);
    }
}
