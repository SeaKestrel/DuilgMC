package fr.kestrel.duilgmc.events;

import fr.kestrel.duilgmc.entitypocket.EntityPocket;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UndoStrip implements Listener {

    @EventHandler
    public void undoStrip(PlayerInteractEvent e){
        //Check for the right item (bark)
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getItem() == null) return;
        if(e.getItem().getType() != Material.PAPER) return;
        if(!e.getItem().hasItemMeta()) return;
        if(!e.getItem().getItemMeta().hasCustomModelData()) return;
        if(e.getItem().getItemMeta().getCustomModelData() != 10) return;
        if(e.getClickedBlock() == null) return;
        if(!e.getClickedBlock().getType().toString().contains("STRIPPED_")) return;
        // Get the new id the block will have and set it
        e.getClickedBlock().setType(Material.valueOf(e.getClickedBlock().getType().toString().replace("STRIPPED_", "")));
        // Remove one bark from the player's inventory
        e.getItem().setAmount(e.getItem().getAmount() - 1);
        // Play a sound for players
        for(Player p : Bukkit.getOnlinePlayers()){
            p.playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_SLIME_BLOCK_BREAK, 0.5f, 1f);
        }
        e.getClickedBlock().getLocation().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(),EntityPocket.getEntityPocket());

        ItemStack bark = new ItemStack(Material.PAPER);
        ItemMeta meta = bark.getItemMeta();
        meta.setCustomModelData(12);
        meta.setDisplayName(ChatColor.YELLOW+"Turn Tool");
        bark.setItemMeta(meta);
        e.getPlayer().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), bark);
    }

}
