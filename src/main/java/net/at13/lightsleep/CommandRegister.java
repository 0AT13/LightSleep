package net.at13.lightsleep;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRegister implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try {

            Integer.valueOf(args[0]);

            if (sender instanceof Player && Integer.parseInt(args[0]) <= 100 && Integer.parseInt(args[0]) >= 0) {

                LightSleep.percentNeedToSleep = Integer.parseInt(args[0]);
                sender.sendMessage(
                        ChatColor.BLUE + "[LightSleep]" +
                        ChatColor.WHITE +  " Percent to sleep was changed to " + LightSleep.percentNeedToSleep + "%"
                );
            }
            else {
                sender.sendMessage(
                        ChatColor.BLUE + "[LightSleep]" +
                        ChatColor.WHITE + " Entered wrong value"
                );
            }
        } catch (NumberFormatException e) {

            sender.sendMessage(
                    ChatColor.BLUE + "[LightSleep]" +
                    ChatColor.WHITE + " Entered wrong value"
            );
        }

        return true;
    }
}
