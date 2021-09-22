package fr.kestrel.duilgmc.skills;

import fr.kestrel.duilgmc.SkillConfig;
import fr.kestrel.duilgmc.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.PacketPlayOutAdvancements;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class SkillsHandler implements Listener {

    @EventHandler
    public void breake(BlockBreakEvent e){
        if(!e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) return;
        if(Main.blocks.getBlocks().contains(e.getBlock())) return;
        Skill s = Main.getSkillProfiles().get(e.getPlayer().getUniqueId()).getSkill(this.parseSkill(e.getBlock()));
        switch(e.getBlock().getType()) {
            case ACACIA_LOG, BIRCH_LOG, DARK_OAK_LOG, JUNGLE_LOG, OAK_LOG, SPRUCE_LOG, GOLD_ORE, DEEPSLATE_GOLD_ORE, CARROTS, POTATOES, NETHER_WART -> s.addExp(6, e.getPlayer());
            case DIRT, STONE, ANDESITE, GRANITE, DIORITE, SAND, GRASS, GRAVEL -> s.addExp(1, e.getPlayer());
            case COAL_ORE, DEEPSLATE_COAL_ORE, NETHER_GOLD_ORE -> s.addExp(3, e.getPlayer());
            case LAPIS_ORE, DEEPSLATE_LAPIS_ORE, REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE, NETHER_QUARTZ_ORE, SUGAR_CANE, CACTUS, WHEAT -> s.addExp(4, e.getPlayer());
            case IRON_ORE, DEEPSLATE_IRON_ORE, COPPER_ORE, DEEPSLATE_COPPER_ORE, BEETROOTS -> s.addExp(5, e.getPlayer());
            case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE, EMERALD_ORE -> s.addExp(10, e.getPlayer());
            case ANCIENT_DEBRIS -> s.addExp(25, e.getPlayer());
            default -> { return; }
        }
        if(e.getBlock().getType().equals(Material.SUGAR_CANE)){
            if(e.getBlock().getLocation().clone().add(0,1,0).getBlock().getType().equals(Material.SUGAR_CANE)){
                s.addExp(4, e.getPlayer());
            }
        }
        parseLevel(Main.getSkillProfiles().get(e.getPlayer().getUniqueId()), s);
    }

    @EventHandler
    public void die(EntityDeathEvent e){
        if(e.getEntity().getType() == EntityType.PLAYER) return;
        if(e.getEntity().getKiller() == null) return;
        Player k = e.getEntity().getKiller();
        Skill s = Main.getSkillProfiles().get(k.getUniqueId()).getSkill(SkillType.HUNTER);
        switch (e.getEntity().getType()) {
            case ZOMBIE, DROWNED, HUSK, VEX, ZOMBIE_VILLAGER, ZOMBIFIED_PIGLIN, PIG, COW, SHEEP, CHICKEN, HORSE, MULE, MUSHROOM_COW, DONKEY, LLAMA, TRADER_LLAMA -> s.addExp(4, k);
            case BLAZE, GUARDIAN, PILLAGER, ILLUSIONER -> s.addExp(8, k);
            case ENDERMAN -> s.addExp(3, k);
            case WITCH, SLIME, MAGMA_CUBE, PHANTOM, PIGLIN_BRUTE -> s.addExp(10, k);
            case ENDERMITE, SILVERFISH, SPIDER, CAVE_SPIDER -> s.addExp(6, k);
            case SQUID, GLOW_SQUID -> s.addExp(1, k);
            case SHULKER, PIGLIN, CREEPER, SKELETON, SKELETON_HORSE, WITHER_SKELETON, STRAY -> s.addExp(5, k);
            case GHAST -> s.addExp(15, k);
            case ENDER_DRAGON, WITHER -> s.addExp(50, k);
        }


        parseLevel(Main.getSkillProfiles().get(k.getUniqueId()), s);
    }

    @EventHandler
    public void place(BlockPlaceEvent e){
        if(!e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) return;
        Main.addBlock(e.getBlock());
    }

    @EventHandler
    public void clicki(PlayerInteractEvent e){
        if(e.getHand() == EquipmentSlot.HAND) return;
        if(!e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) return;
        if(e.getClickedBlock() == null) return;
        if(e.getClickedBlock().getType() == Material.SUGAR_CANE){
            if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.BONE_MEAL)){
                if(e.getClickedBlock().getLocation().clone().add(0,1,0).getBlock().getType().equals(Material.AIR)){
                    if(e.getClickedBlock().getLocation().clone().add(0,-1,0).getBlock().getType().equals(Material.SUGAR_CANE) && e.getClickedBlock().getLocation().clone().add(0,-2,0).getBlock().getType().equals(Material.SUGAR_CANE)) return;
                    e.getClickedBlock().getLocation().clone().add(0,1,0).getBlock().setType(Material.SUGAR_CANE);
                    e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                } else {
                    if(e.getClickedBlock().getLocation().clone().add(0,2,0).getBlock().getType().equals(Material.AIR)){
                        if(e.getClickedBlock().getLocation().clone().add(0,-1,0).getBlock().getType().equals(Material.SUGAR_CANE)) return;
                        e.getClickedBlock().getLocation().clone().add(0,2,0).getBlock().setType(Material.SUGAR_CANE);
                        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    } else {
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        if(!SkillConfig.config.contains("players."+uuid.toString())){
            e.getPlayer().sendMessage(ChatColor.GREEN+"Nouvelle mise à jour : . Pour en découvrir plus, /skills");
            SkillProfile profile = new SkillProfile(e.getPlayer().getUniqueId());
            for(SkillType type : SkillType.values()){
                Skill skill = new Skill(type, 0, 0);
                profile.addSkill(skill);
            }
            Main.addSkillProfile(e.getPlayer().getUniqueId(), profile);
            Main.config.save();
        } else if(SkillConfig.config.getConfigurationSection("players."+uuid.toString()+".skills.hunter") == null) {
            SkillConfig.config.set("players."+uuid.toString()+".skills.hunter.exp", 0);
            SkillConfig.config.set("players."+uuid.toString()+".skills.hunter.level", 0);
            SkillProfile profile = Main.getSkillProfiles().get(uuid);
            Skill skill = new Skill(SkillType.HUNTER, 0, 0);
            profile.addSkill(skill);
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent e){
        Main.config.save();
    }

    @EventHandler
    public void inv(InventoryClickEvent e){
        if(!e.getView().getTitle().equals("Skills")) return;
        e.setCancelled(true);
    }

    public static int getLevelByExp(double exp){
        if(exp > 522425) return 20;
        if(exp > 322425) return 19;
        if(exp > 222425) return 18;
        if(exp > 147425) return 17;
        if(exp > 97425) return 16;
        if(exp > 67425) return 15;
        if(exp > 47425) return 14;
        if(exp > 32425) return 13;
        if(exp > 22425) return 12;
        if(exp > 14925) return 11;
        if(exp > 9925) return 10;
        if(exp > 6425) return 9;
        if(exp > 4425) return 8;
        if(exp > 2925) return 7;
        if(exp > 1925) return 6;
        if(exp > 1175) return 5;
        if(exp > 675) return 4;
        if(exp > 375) return 3;
        if(exp > 175) return 2;
        if(exp > 50) return 1;
        return 0;
    }

    public static void parseLevel(SkillProfile skillProfile, Skill skill){
        Skill s = skill;
        if(s.getLevel() < getLevelByExp(s.getExp())) {
            String id = s.getType().getId();
            String firstLetter = id.substring(0, 1).toUpperCase();
            String remainingLetters = id.substring(1, id.length());
            id = firstLetter + remainingLetters;
            if(getLevelByExp(s.getExp()) == 20){
                ClientboundSetTitleTextPacket titlepack = new ClientboundSetTitleTextPacket(IChatBaseComponent.a(ChatColor.GREEN+"Niveau max de "+ChatColor.DARK_GREEN+id+" "+ChatColor.GREEN+" atteinds !"));
                ((CraftPlayer)skillProfile.getPlayer()).getHandle().b.sendPacket(titlepack);
                ClientboundSetSubtitleTextPacket stp = new ClientboundSetSubtitleTextPacket(IChatBaseComponent.a(ChatColor.GREEN+"Récompense ! "));
                ((CraftPlayer)skillProfile.getPlayer()).getHandle().b.sendPacket(stp);
                switch(s.getType()){
                    case FARMER:
                        ItemStack a = new ItemStack(Material.NETHERITE_HOE);
                        a.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
                        skillProfile.getPlayer().getWorld().dropItem(skillProfile.getPlayer().getLocation(), a);
                        break;
                    case MINER:
                        ItemStack b = new ItemStack(Material.NETHERITE_PICKAXE);
                        b.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
                        b.addUnsafeEnchantment(Enchantment.DIG_SPEED, 6);
                        b.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 4);
                        skillProfile.getPlayer().getWorld().dropItem(skillProfile.getPlayer().getLocation(), b);
                        break;
                    case FORAGER:
                        ItemStack c = new ItemStack(Material.NETHERITE_AXE);
                        c.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
                        c.addUnsafeEnchantment(Enchantment.DIG_SPEED, 6);
                        c.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 4);
                        skillProfile.getPlayer().getWorld().dropItem(skillProfile.getPlayer().getLocation(), c);
                        break;
                    case HUNTER:
                        ItemStack d = new ItemStack(Material.NETHERITE_SWORD);
                        d.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 6);
                        d.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
                        d.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 4);
                        skillProfile.getPlayer().getWorld().dropItem(skillProfile.getPlayer().getLocation(), d);
                        break;
                }
            } else {
                ClientboundSetTitleTextPacket titlepack = new ClientboundSetTitleTextPacket(IChatBaseComponent.a(ChatColor.GREEN+"+1 niveau en "+ChatColor.DARK_GREEN+id));
                ((CraftPlayer)skillProfile.getPlayer()).getHandle().b.sendPacket(titlepack);
                ClientboundSetSubtitleTextPacket stp = new ClientboundSetSubtitleTextPacket(IChatBaseComponent.a(ChatColor.GREEN+"Tu es maintenant niveau "+ChatColor.DARK_GREEN+getLevelByExp(s.getExp())));
                ((CraftPlayer)skillProfile.getPlayer()).getHandle().b.sendPacket(stp);
            }
        }
        skill.setLevel(getLevelByExp(s.getExp()));
    }

    public SkillType parseSkill(Block b){
        String bT = b.getType().toString();
        if(bT.contains("_LOG")) {
            return SkillType.FORAGER;
        }else if(bT.contains("_ORE") || bT.equals("ANCIENT_DEBRIS") || bT.equals("DIRT") || bT.equals("STONE") || bT.equals("ANDESITE") || bT.equals("GRANITE") || bT.equals("DIORITE") || bT.equals("SAND") || bT.equals("GRASS") || bT.equals("GRAVEL")) {
            return SkillType.MINER;
        } else if(bT.contains("SUGAR_CANE") || bT.contains("CACTUS") || bT.contains("CARROTS") || bT.contains("POTATOES") || bT.contains("BEETROOTS") ||bT.contains("NETHER_WART") ||bT.contains("WHEAT")){
            return SkillType.FARMER;
        }
        return null;
    }

}
