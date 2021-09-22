package fr.kestrel.duilgmc;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class ExtractEnch implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.getInventory().contains(Material.BOOK)){
                if(player.getInventory().getItemInMainHand() == null){
                    player.sendMessage("Peux-tu tenir l'item que tu souhaites désenchanter dans ta main s'il te plaît ?");
                } else {
                    ItemStack hand = player.getInventory().getItemInMainHand();
                    if(hand.getItemMeta().getEnchants().isEmpty()){
                        player.sendMessage("Cet item n'a aucun enchantement à retirer !");
                    } else {
                        ItemStack enchBook = new ItemStack(Material.ENCHANTED_BOOK);
                        EnchantmentStorageMeta enchBookMeta = (EnchantmentStorageMeta) enchBook.getItemMeta();
                        for(Enchantment enchantment : hand.getItemMeta().getEnchants().keySet()){
                            enchBookMeta.addStoredEnchant(enchantment, hand.getEnchantmentLevel(enchantment), true);
                            hand.removeEnchantment(enchantment);
                        }
                        enchBook.setItemMeta(enchBookMeta);
                        for(ItemStack it : player.getInventory().getContents()){
                            if(it == null) continue;
                            if(it.getType() != Material.BOOK) continue;
                            it.setAmount(it.getAmount() - 1);
                        }
                        player.getInventory().addItem(enchBook);
                    }
                }
            } else {
                player.sendMessage("Tu n'as pas de livre dans ton inventaire pour extraire les enchantements.");
            }
        }else {
            commandSender.sendMessage("Only players can do this !");
        }

        return true;
    }
}
