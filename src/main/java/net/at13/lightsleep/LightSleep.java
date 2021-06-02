package net.at13.lightsleep;

import net.at13.lightsleep.players.SleepingPlayers;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public final class LightSleep extends JavaPlugin {

    private final SleepingPlayers sleepingPlayers = new SleepingPlayers();
    private static Plugin plugin;

    String configName = "config.txt";
    String workingDirectory = "plugins/LightSleep";

    public static int percentNeedToSleep = 60;

    private final EventListener eventListener = new EventListener(sleepingPlayers);

    @Override
    public void onEnable() {

        plugin = this;

        String configFilePath = workingDirectory + "/" + configName;

        try {
            //Creating a File object
            File configDirectory = new File(workingDirectory);

            //Creating the directory
            if (configDirectory.mkdir()) {
                System.out.println("Directory created successfully.");
            } else {
                System.out.println("Directory already exist.");
            }

            File configFile = new File(configFilePath);

            if (configFile.createNewFile()) {
                System.out.println("Config file created: " + configName);

                FileWriter configWriter = new FileWriter(configFilePath);

                configWriter.write(String.valueOf(percentNeedToSleep));
                configWriter.close();
            } else {
                System.out.println("Config file already exists.");

                Scanner configReader = new Scanner(configFile);

                percentNeedToSleep = Integer.parseInt(configReader.nextLine());
                configReader.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        this.getCommand("changepercent").setExecutor(new CommandRegister());

        getServer().getPluginManager().registerEvents(eventListener, this);

        System.out.println(ChatColor.LIGHT_PURPLE + "[" + Bukkit.getServer().getName() + "]" + ChatColor.BLUE + "[LightSleep] " + ChatColor.WHITE + " LightSleep launched successfully - created by AT13");
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        String configFilePath = workingDirectory + File.separator + configName;

        try {
            FileWriter configWriter = new FileWriter(configFilePath);

            configWriter.write(String.valueOf(percentNeedToSleep));
            configWriter.close();
            System.out.println("Successfully wrote to the config file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
