package fr.kestrel.duilgmc.events;

import fr.kestrel.duilgmc.customentities.Snail;
import fr.kestrel.duilgmc.netheritegolem.NetheriteGolem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GetBark implements Listener {

    @EventHandler
    public void getBark(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getClickedBlock() == null) return;
        if(!e.getClickedBlock().getType().toString().contains("_LOG")) return;
        if(e.getClickedBlock().getType().toString().contains("STRIPPED_")) return;
        if(e.getItem() == null) return;
        if(!e.getItem().getType().toString().contains("_AXE")) return;
        ItemStack bark = new ItemStack(Material.PAPER);
        ItemMeta meta = bark.getItemMeta();
        meta.setCustomModelData(10);
        meta.setDisplayName(ChatColor.WHITE+"Bark");
        bark.setItemMeta(meta);
        e.getPlayer().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), bark);
        Snail snail = new Snail(e.getClickedBlock().getLocation().add(1,1,1));
        NetheriteGolem g = new NetheriteGolem(e.getClickedBlock().getLocation());
        ((CraftWorld)e.getClickedBlock().getLocation().getWorld()).getHandle().addEntity(snail);
    }

}
