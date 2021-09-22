package fr.kestrel.duilgmc.netheritegolem;

import fr.kestrel.duilgmc.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.EntityGolem;
import net.minecraft.world.level.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class NetheriteGolemEvents implements Listener {

    private BossBar bossBar = Bukkit.createBossBar( new NamespacedKey(Main.getPlugin(Main.class), "NetherGolem"), "Le boss des boss !", BarColor.RED, BarStyle.SEGMENTED_20, BarFlag.PLAY_BOSS_MUSIC, BarFlag.CREATE_FOG, BarFlag.DARKEN_SKY);

    public void spawnGolem(Location loc){
        /*Golem golem = (Golem) loc.getWorld().spawnEntity(loc, EntityType.IRON_GOLEM);
        Entity golemEnt = ((CraftEntity) golem).getHandle();
        golemEnt.setCustomName(new ChatComponentText(ChatColor.of("#332F2C") + "" + ChatColor.BOLD + "Netherite Golem §r§c"+Math.round(golem.getHealth() * 5)+"❤"));
        golemEnt.setCustomNameVisible(true);*/
        NetheriteGolem golem = new NetheriteGolem(loc.clone().add(0, 255-loc.getY(), 0));
        ((CraftWorld)loc.getWorld()).addEntity(golem, CreatureSpawnEvent.SpawnReason.CUSTOM);
        loc.getWorld().createExplosion(loc, 5);
        for (Player p : Bukkit.getOnlinePlayers()){
            p.playSound(p.getLocation(), Sound.ENTITY_IRON_GOLEM_DEATH, SoundCategory.MASTER, 999, 1);
        }
        bossBar.setProgress(1.0);
        bossBar.removeAll();
        for(Entity e : golem.getLocation().getWorld().getNearbyEntities(golem.getLocation(), 100, 255, 100)){
            if(e.getType().equals(EntityType.PLAYER)){
                bossBar.addPlayer(((Player) e));
            }
        }
    }

    @EventHandler
    public void spawnGolem(BlockPlaceEvent e){
        if(!e.getBlock().getType().equals(Material.CARVED_PUMPKIN)) return;
        if(e.getBlock().getLocation().clone().subtract(0, 1, 0).getBlock().getType().equals(Material.BEACON)){
            if(e.getBlock().getLocation().clone().subtract(0,2,0).getBlock().getType().equals(Material.DIAMOND_BLOCK)){
                if(e.getBlock().getLocation().clone().subtract(0,1,1).getBlock().getType().equals(Material.DIAMOND_BLOCK)){
                    if(e.getBlock().getLocation().clone().subtract(0,1,-1).getBlock().getType().equals(Material.DIAMOND_BLOCK)){
                        e.getBlock().setType(Material.AIR);
                        e.getBlock().getLocation().clone().subtract(0, 1, 0).getBlock().setType(Material.AIR);
                        e.getBlock().getLocation().clone().subtract(0, 2, 0).getBlock().setType(Material.AIR);
                        e.getBlock().getLocation().clone().subtract(0, 1, 1).getBlock().setType(Material.AIR);
                        e.getBlock().getLocation().clone().subtract(0, 1, -1).getBlock().setType(Material.AIR);
                        spawnGolem(e.getBlock().getLocation().clone().subtract(0, 1, 0));
                    }
                } else if (e.getBlock().getLocation().clone().subtract(1,1,0).getBlock().getType().equals(Material.DIAMOND_BLOCK)) {
                    if (e.getBlock().getLocation().clone().subtract(-1,1,0).getBlock().getType().equals(Material.DIAMOND_BLOCK)) {
                        e.getBlock().setType(Material.AIR);
                        e.getBlock().getLocation().clone().subtract(0, 1, 0).getBlock().setType(Material.AIR);
                        e.getBlock().getLocation().clone().subtract(0, 2, 0).getBlock().setType(Material.AIR);
                        e.getBlock().getLocation().clone().subtract(1, 1, 0).getBlock().setType(Material.AIR);
                        e.getBlock().getLocation().clone().subtract(-1, 1, 0).getBlock().setType(Material.AIR);
                        spawnGolem(e.getBlock().getLocation().clone().subtract(0, 1, 0));
                    }
                }
            }
        }
    }

    @EventHandler
    public void hurt(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Golem){
            if(e.getDamager().getCustomName().equals("Le boss des boss !")) e.setDamage(25);
        } else if(e.getEntity() instanceof Golem) {
            if(e.getEntity().getCustomName() != null || e.getEntity().getCustomName().equals("Le boss des boss !")) {
                double d = e.getFinalDamage() / 5;
                Random r = new Random();
                int tour = r.nextInt(4);
                if(tour == 1) {
                    e.setCancelled(true);
                    if(e.getDamager() instanceof Player) ((Player) e.getDamager()).playSound(e.getDamager().getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, -1);
                } else {
                    int back = r.nextInt(10);
                    e.setDamage(d);
                    if(back == 1) {
                        if(e.getDamager() instanceof Player) {
                            ((Player) e.getDamager()).damage(d/3);
                            e.getDamager().sendMessage("le boss des boss t'a renvoyé ton coup !");
                        } else {
                            e.setCancelled(true);
                        }
                    }
                }
                bossBar.setProgress(((((Golem) e.getEntity()).getHealth()) * 2) / 200);
            }
        }
    }

    @EventHandler
    public void die(EntityDeathEvent e){
        if(e.getEntity() instanceof Golem){
            if(e.getEntity().getCustomName() != null && e.getEntity().getCustomName().equals("Le boss des boss !")) {
                e.getDrops().clear();
                e.getDrops().add(Main.getEnderiumIngot());
                e.setDroppedExp(200);
                bossBar.removeAll();
            }
        }
    }

    @EventHandler
    public void hurt2(EntityDamageEvent e){
        if(e.getEntity() instanceof Golem){
            if(e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) e.setCancelled(true);
            if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                Bukkit.broadcastMessage("Le Golem du Nether a spawné !");
                /*for(Player p : Bukkit.getOnlinePlayers()){
                    p.spawnParticle(Particle.EXPLOSION_HUGE, e.getEntity().getLocation(), 1);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.playSound(e.getEntity().getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1, 1);
                        }
                    }.runTaskLater(Main.getPlugin(Main.class), 1);
                }*/
                e.setCancelled(true);
            }
        }
    }

}
