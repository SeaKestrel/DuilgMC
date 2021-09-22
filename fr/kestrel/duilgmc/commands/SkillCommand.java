package fr.kestrel.duilgmc.commands;

import fr.kestrel.duilgmc.Main;
import fr.kestrel.duilgmc.skills.SkillProfile;
import fr.kestrel.duilgmc.skills.SkillType;
import net.minecraft.world.inventory.Slot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SkillCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = ((Player) commandSender);
            Inventory inv = Bukkit.createInventory(null, 54, "Skills");
            SkillProfile p = Main.getSkillProfiles().get(player.getUniqueId());
            int n = 8;
            for(SkillType t : SkillType.values()){
                ItemStack i = new ItemStack(t.getItem());
                ItemMeta m = i.getItemMeta();
                String id = p.getSkill(t).getType().getId();
                String firstLetter = id.substring(0, 1).toUpperCase();
                String remainingLetters = id.substring(1, id.length());
                id = firstLetter + remainingLetters;
                m.setDisplayName((p.getSkill(t).getLevel() < 20 ? ChatColor.RED : ChatColor.GREEN)+id+" level "+p.getSkill(t).getLevel()+"/20");
                m.setLore(Arrays.asList(
                        ChatColor.GRAY+"Exp to next Level :",
                        ChatColor.GRAY+""+p.getSkill(t).getExpToNextLevel()
                ));
                m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                i.setItemMeta(m);
                inv.setItem(n+2, i);
                int v = n+2;
                for(int z = 1; z<=3; z++) {
                    ItemStack it1 = new ItemStack(Material.RED_BANNER);
                    int l = p.getSkill(t).getLevel();
                    if(z == 1){
                        if(l < 6){
                            it1.setType(Material.YELLOW_STAINED_GLASS_PANE);
                        } else if (l >= 6){
                            it1.setType(Material.LIME_STAINED_GLASS_PANE);
                        } else {
                            it1.setType(Material.RED_STAINED_GLASS_PANE);
                        }
                    } else if( z == 2) {
                        if(l < 6){
                            it1.setType(Material.RED_STAINED_GLASS_PANE);
                        } else if (l >= 6 && l <= 13){
                            it1.setType(Material.YELLOW_STAINED_GLASS_PANE);
                        } else if(l > 13){
                            it1.setType(Material.GREEN_STAINED_GLASS_PANE);
                        } else {
                            it1.setType(Material.RED_STAINED_GLASS_PANE);
                        }
                    } else if(z == 3){
                        if(l < 13){
                            it1.setType(Material.RED_STAINED_GLASS_PANE);
                        } else if (l > 13 && l < 20) {
                            it1.setType(Material.YELLOW_STAINED_GLASS_PANE);
                        } else if (l >= 20){
                            it1.setType(Material.LIME_STAINED_GLASS_PANE);
                        }
                    } else {
                        it1.setType(Material.RED_STAINED_GLASS_PANE);
                    }
                    ItemMeta im1 = it1.getItemMeta();
                    im1.setDisplayName((p.getSkill(t).getLevel() < 20 ? ChatColor.RED : ChatColor.GREEN)+id+" level "+p.getSkill(t).getLevel()+"/20");
                    im1.setLore(Arrays.asList(
                            ChatColor.GRAY+"Exp to next Level :",
                            ChatColor.GRAY+""+p.getSkill(t).getExpToNextLevel()
                    ));
                    it1.setItemMeta(im1);
                    inv.setItem(v+9, it1);
                    v += 9;
                }
                int items = 0;
                ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta em = empty.getItemMeta();
                em.setDisplayName(" ");
                em.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                empty.setItemMeta(em);
                for(ItemStack slot : inv.getContents()) {
                    if(slot == null){
                        inv.setItem(items, empty);
                    }
                    items++;
                    continue;
                }
                n += 2;
            }
            player.openInventory(inv);
        }else {

        }
        return true;
    }
}
