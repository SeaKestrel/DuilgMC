package fr.kestrel.duilgmc.commands;

import fr.kestrel.duilgmc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemSend implements CommandExecutor {

    public ItemStack items;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(strings.length < 1) {
                player.sendMessage("Peux-tu mentionner le destinataire de l'item ?");
            } else {
                if (Bukkit.getPlayer(strings[0]) == null){
                    player.sendMessage("Ce destinataire n'existe pas ou n'est pas connecté.");
                } else {
                    if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                        player.sendMessage("Peux-tu tenir l'item que tu veux envoyer dans ta main ?");
                    } else {
                        Player receiver = Bukkit.getPlayer(strings[0]);
                        Location receiverLocation = receiver.getLocation();
                        Location senderLocation = player.getLocation();
                        if (receiverLocation.getWorld().getUID() != senderLocation.getWorld().getUID()){
                            player.sendMessage("Ce joueur n'est pas disponible pour le moment.");
                        }else {
                            double distance = senderLocation.distance(receiverLocation);
                            long time = (long) (0.01 * (player.getInventory().getItemInMainHand().getAmount()) + 0.005 * (distance));
                            player.sendMessage("Tu as envoyé un ou des item(s) à "+receiver.getDisplayName()+" qui arriveront dans "+time+" s.");
                            items = new ItemStack(player.getInventory().getItemInMainHand().getType(), player.getInventory().getItemInMainHand().getAmount());
                            items.setItemMeta(player.getInventory().getItemInMainHand().getItemMeta());
                            player.getInventory().getItemInMainHand().setAmount(0);
                            /*Bukkit.getPlayer(strings[0]).getInventory().addItem(items);
                            receiver.sendMessage("Tu as reçu un ou des items de la part de "+player.getDisplayName()+".");
                            player.sendMessage(receiver.getDisplayName() + " a bien reçu tes items.");*/
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Bukkit.getPlayer(strings[0]).getInventory().addItem(items);
                                    receiver.sendMessage("Tu as reçu un ou des items de la part de "+player.getDisplayName()+".");
                                    player.sendMessage(receiver.getDisplayName() + " a bien reçu tes items.");
                                }
                            }.runTaskLaterAsynchronously(Main.getPlugin(Main.class), (time * 20));
                        }
                    }
                }
            }
        } else {
            commandSender.sendMessage("Only players can do this.");
        }
        return true;
    }
}
