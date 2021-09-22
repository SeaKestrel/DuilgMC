package fr.kestrel.duilgmc;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Crafts implements Listener {

    @EventHandler
    public void craft(PlayerJoinEvent e){
        e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
    }

    @EventHandler
    public void block(BlockPlaceEvent e){
        if(e.getBlock().getType() == Material.CRYING_OBSIDIAN) {
            if(!e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) return;
            if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.YELLOW+"Nethered Obsidian")){
                e.setCancelled(true);
            }
        }
    }

}
