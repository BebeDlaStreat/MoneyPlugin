package fr.bebedlastreat.money.commands;

import fr.bebedlastreat.money.Main;
import fr.bebedlastreat.money.database.Money;
import fr.bebedlastreat.money.utils.PlayerInfos;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MoneyCommand implements CommandExecutor {
    private Main main;
    public MoneyCommand(Main main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("coins.edit") || args.length == 0) {
                player.sendMessage("§7§m--------------------\n" +
                        "§eVous avez §6" + Money.getMoney(player.getUniqueId()) + " §ecoins\n" +
                        "§7§m--------------------");
                return false;
            }
        }
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            if (args.length < 3) {
                sender.sendMessage("/" + label + " add/set/remove {player} {number}");
                return;
            }
            String targetName = args[1];
            Player targetPlayer = Bukkit.getPlayer(targetName);
            UUID targetUUID;
            if (targetPlayer != null) {
                targetUUID = targetPlayer.getUniqueId();
            } else {
                targetUUID = PlayerInfos.getUuidFromName(targetName);
            }
            if (targetUUID == null) {
                sender.sendMessage("§4Ce joueur n'existe pas !");
                return;
            }

            double number;
            try {
                number = Integer.parseInt(args[2]);

            } catch (NumberFormatException e) {
                sender.sendMessage("§4Le troisième argument n'est pas un nombre !");
                return;
            }
            if (args[0].equals("add")) {
                Money.addMoney(targetUUID, number);
                sender.sendMessage("§a" + number + " §ecoins ont été ajouté à §a" + args[1]);
            }
            if (args[0].equals("remove")) {
                Money.removeMoney(targetUUID, number);
                sender.sendMessage("§a" + number + " §ecoins ont été retiré à §a" + args[1]);
            }
            if (args[0].equals("set")) {
                Money.setMoney(targetUUID, number);
                sender.sendMessage("§a" + number + " §ecoins ont été défini à §a" + args[1]);
            }
        });
        return false;
    }
}
