package fr.kestrel.duilgmc.patched;

import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DropsPatch implements Listener {

    @EventHandler
    public void drop(EntityDeathEvent e){

        if(e.getEntity() instanceof IronGolem) {
            e.getDrops().clear();
            e.setDroppedExp(0);
            int ironIngot = 3 + (int)(Math.random() * ((5 - 3) + 1));
            int poppys = 0 + (int)(Math.random() * ((5 - 0) + 1));
            e.getDrops().add(new ItemStack(Material.IRON_INGOT, ironIngot));
            if(poppys == 2){
                e.getDrops().add(new ItemStack(Material.POPPY, 1));
            }
        } else if (e.getEntity() instanceof Enderman){
            e.getDrops().clear();
        }

    }

}
