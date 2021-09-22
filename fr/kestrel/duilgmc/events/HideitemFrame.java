package fr.kestrel.duilgmc.events;

import fr.kestrel.duilgmc.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;//rl//
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class HideitemFrame implements Listener {

    @EventHandler
    public void i(PlayerInteractAtEntityEvent e){
        if(e.getHand() == EquipmentSlot.OFF_HAND) return;
        if(!e.getPlayer().isSneaking()) return;
        if(e.getRightClicked().getType() == EntityType.ITEM_FRAME){
            if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.SHEARS){
                ItemFrame a = (ItemFrame) e.getRightClicked();
                a.setVisible(false);
                new BukkitRunnable(){
                    @Override
                    public void run(){
                        if(a.getItem().getType() == Material.AIR) {
                            a.setItem(new ItemStack(Material.AIR));
                            e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.SHEARS));
                        }
                    }
                }.runTaskLater(Main.getPlugin(Main.class), 1);
                System.out.println(e.getPlayer().getName()+" a caché une itemframe.");
            }
        }
    }

}
