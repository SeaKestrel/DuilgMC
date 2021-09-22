package fr.kestrel.duilgmc.mineral;

import fr.kestrel.duilgmc.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlackOre implements Listener {
    public static ItemStack getBlackOre(){
        ItemStack ore = new ItemStack(Material.OBSIDIAN);
        ItemMeta m = ore.getItemMeta();
        m.setDisplayName("Black Ore");
        m.setCustomModelData(11);
        ore.setItemMeta(m);
        return ore;
    }

    @EventHandler
    public void handleMine(BlockBreakEvent e){
        if(Main.hasOre(e.getBlock().getLocation())){
            e.getPlayer().sendMessage("U just found a black ore!");
        }
    }

    @EventHandler
    public void place(BlockPlaceEvent e){
        if(e.getBlock().getType() == Material.OBSIDIAN){
            Main.addBlackOre(e.getBlock().getLocation().serialize().toString());
            System.out.println(e.getBlock().getLocation().serialize());
        }
    }

}
