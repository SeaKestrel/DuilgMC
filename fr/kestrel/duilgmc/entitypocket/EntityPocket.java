package fr.kestrel.duilgmc.entitypocket;

import fr.kestrel.duilgmc.Main;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.world.entity.Entity;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Locale;

public class EntityPocket implements Listener {

    public static ItemStack getEntityPocket(){
        ItemStack a = new ItemStack(Material.EGG);
        ItemMeta b = a.getItemMeta();
        b.setDisplayName(ChatColor.YELLOW+"Entity Pocket");
        b.setCustomModelData(11);
        a.setItemMeta(b);
        return a;
    }

    public static ShapedRecipe getRecipe(){
        ShapedRecipe c = new ShapedRecipe(new NamespacedKey(Main.getPlugin(Main.class), "entity_pocket"), getEntityPocket());
        c.shape("GCG", "CBC", "GCG");
        c.setIngredient('G', Material.GOLD_BLOCK);
        c.setIngredient('C', Material.COPPER_BLOCK);
        c.setIngredient('B', Material.BUCKET);
        return c;
    }

    // Intercepter le clic pour empêcher le lancé de l'oeuf
    @EventHandler
    public void clickHandle(PlayerInteractEvent e) {
        if(e.getItem() == null) return;
        if(e.getItem().getType() != Material.EGG) return;
        if(!e.getItem().hasItemMeta()) return;
        if(!e.getItem().getItemMeta().hasCustomModelData()) return;
        if(e.getItem().getItemMeta().getCustomModelData() != 11) return;
        if(!e.getItem().getItemMeta().hasDisplayName()) return;
        if(e.getAction().toString().contains("LEFT")) return;
        e.setCancelled(true);
    }
    @EventHandler
    public void catchEntity(PlayerInteractAtEntityEvent e) {
        // Only get one hand to avoid the event trigger twice
        // Check for the right item (entity pocket)
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if(item.getType() == Material.EGG) {
            if(e.getHand() == EquipmentSlot.OFF_HAND) return;
            if(!item.hasItemMeta()) return;
            ItemMeta meta = item.getItemMeta();
            if(!meta.hasDisplayName()) return;
            if(!meta.hasCustomModelData()) return;
            if(meta.getCustomModelData() != 11) return;
            // Handle entity type (to avoid error for no-egg entities like exp or dragon
            EntityType ent = e.getRightClicked().getType();
            String material = ent.toString()+"_SPAWN_EGG";
            try{
                Material mat = Material.valueOf(material);
                ItemStack egg = new ItemStack(mat);
                item.setAmount(item.getAmount() - 1);
                e.getRightClicked().getWorld().dropItemNaturally(e.getRightClicked().getLocation(), egg);
                ((CraftEntity)e.getRightClicked()).getHandle().setRemoved(Entity.RemovalReason.a);
                for (Player p : Bukkit.getOnlinePlayers()){
                    p.playSound(e.getRightClicked().getLocation(), Sound.BLOCK_SHULKER_BOX_CLOSE, 0.5f, 0.5f);
                }
            } catch (IllegalArgumentException ex){
                e.setCancelled(true);
                return;
            }
        } else if(item.getType().toString().contains("_SPAWN_EGG")) {
            EntityType type = EntityType.valueOf(item.getType().toString().replace("_SPAWN_EGG", ""));
            if(type != e.getRightClicked().getType()) return;
            e.getPlayer().getWorld().dropItem(e.getRightClicked().getLocation().add(0,1,0), getEntityPocket());
        } else {
            return;
        }
    }

    // The event used to handle the spawn with pokeball
    @EventHandler
    public void spawnEntityOnBlock(PlayerInteractEvent e){
        if(!e.getAction().toString().contains("RIGHT_")) return;
        if(e.getHand() != EquipmentSlot.HAND) return;
        if(e.getItem() == null) return;
        if(!e.getItem().getType().toString().contains("SPAWN_EGG")) return;
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            e.getPlayer().getWorld().dropItem(e.getClickedBlock().getLocation(), getEntityPocket());
        } else if( e.getAction() == Action.RIGHT_CLICK_AIR) {
            Snowball arrow = e.getPlayer().launchProjectile(Snowball.class);
            arrow.setMetadata("pokeball", new FixedMetadataValue(Main.getPlugin(Main.class), e.getItem().getType().toString().replace("_SPAWN_EGG", "")));
        }
    }

    @EventHandler
    public void endFlySnowball(ProjectileHitEvent e){
        if(e.getEntity().getMetadata("pokeball") == null) return;
        EntityType type = EntityType.valueOf(String.valueOf(e.getEntity().getMetadata("pokeball").get(0).value()));
        e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), type);
        e.getEntity().getWorld().spawnParticle(Particle.WHITE_ASH, e.getEntity().getLocation(), 30);
        e.getEntity().getWorld().spawnParticle(Particle.BUBBLE_POP, e.getEntity().getLocation(), 15);
        if(e.getEntity().getShooter() != null && e.getEntity().getShooter() instanceof Player){
            e.getEntity().getWorld().dropItemNaturally(((Player) e.getEntity().getShooter()).getLocation(), this.getEntityPocket()  );
        }
        for(Player p : Bukkit.getOnlinePlayers()){
            p.playSound(e.getEntity().getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 0.4f, 3f);
        }
    }

}
