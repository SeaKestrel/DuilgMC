package fr.kestrel.duilgmc;

import fr.kestrel.duilgmc.commands.ItemSend;
import fr.kestrel.duilgmc.commands.SkillCommand;
import fr.kestrel.duilgmc.entitypocket.EntityPocket;
import fr.kestrel.duilgmc.events.*;
import fr.kestrel.duilgmc.mineral.BlackOre;
import fr.kestrel.duilgmc.patched.DisableEntities;
import fr.kestrel.duilgmc.patched.DropsPatch;
import fr.kestrel.duilgmc.skills.SkillProfile;
import fr.kestrel.duilgmc.skills.SkillsHandler;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Main extends JavaPlugin {

    private static Map<UUID, SkillProfile> skillProfiles = new HashMap<>();
    private static Map<String, Boolean> oresList = new HashMap<>();

    public static SkillConfig config = new SkillConfig();
    public static BlockConfig blocks = new BlockConfig();

    @Override
    public void onEnable() {
        this.getServer().addRecipe(getEnderiumIngotRecipe());
        FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(this, "leather"), new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH, 1, 20*10);
        this.getServer().addRecipe(recipe);
        SmithingRecipe smithingRecipe = new SmithingRecipe(new NamespacedKey(this, "nethered_obsidian"), getReinforcedObi(), new RecipeChoice.MaterialChoice(Material.CRYING_OBSIDIAN), new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT));
        this.getServer().addRecipe(smithingRecipe);
        this.getServer().addRecipe(getChainChestNetherRecipe());
        this.getServer().addRecipe(getGoldLegsNetherRecipe());
        this.getServer().addRecipe(getLethBootsNetherRecipe());
        this.getServer().addRecipe(getEnderiumHelmetRecipe());
        this.getServer().addRecipe(getEnderiumChestRecipe());
        this.getServer().addRecipe(getEnderiumLegRecipe());
        this.getServer().addRecipe(getEnderiumBootsRecipe());
        this.getServer().addRecipe(getEnderiumPickRecipe());
        this.getServer().addRecipe(getEnderiumSwordRecipe());
        this.getServer().addRecipe(getEnderiumAxeRecipe());
        this.getServer().addRecipe(getGunPowderRecipe());
        this.getServer().addRecipe(getBMDHoeRecipe());
        this.getServer().addRecipe(getBMIHoeRecipe());
        this.getServer().addRecipe(getBMNHoeRecipe());
        this.getServer().addRecipe(getCBBRecipe());
        this.getServer().addRecipe(EntityPocket.getRecipe());
        this.getCommand("send").setExecutor(new ItemSend());
        this.getCommand("extractench").setExecutor(new ExtractEnch());
        this.getCommand("skills").setExecutor(new SkillCommand());
        config.setup();
        config.save();
        this.getServer().getPluginManager().registerEvents(new Crafts(), this);
        this.getServer().getPluginManager().registerEvents(new SkillsHandler(), this);
        this.getServer().getPluginManager().registerEvents(new EntityPocket(), this);
        this.getServer().getPluginManager().registerEvents(new DropsPatch(), this);
        this.getServer().getPluginManager().registerEvents(new DisableEntities(), this);
        this.getServer().getPluginManager().registerEvents(new HideitemFrame(), this);
        this.getServer().getPluginManager().registerEvents(new GetBark(), this);
        this.getServer().getPluginManager().registerEvents(new UndoStrip(), this);
        this.getServer().getPluginManager().registerEvents(new TurnBlock(), this);
        this.getServer().getPluginManager().registerEvents(new WorldInit(), this);
        this.getServer().getPluginManager().registerEvents(new BlackOre(), this);
        new BukkitRunnable() {
            @Override
            public void run() {
                config.save();
            }
        }.runTaskTimer(this, 0, 20*5*60);
    }

    public static Map<UUID, SkillProfile> getSkillProfiles() {
        return skillProfiles;
    }

    public static Map<String, Boolean> getOresList() {
        return oresList;
    }

    public static void addBlackOre(String s){
        if(oresList.containsKey(s)) return;
        oresList.put(s, true);
    }

    public static void addSkillProfile(UUID uiud, SkillProfile profile){
        skillProfiles.put(uiud, profile);
        System.out.println("Added skill profile for "+uiud.toString());
    }

    public static boolean hasOre(Location loc) {
        if(oresList.containsKey(loc.serialize().toString())){
            System.out.println("Has ore");
            return true;
        }
        return false;
    }



    public static void addBlock(Block block){
        blocks.getBlocks().add(block);
    }

    public void onDisable(){
        config.save();
        Bukkit.getBossBar(new NamespacedKey(this, "NetherGolem")).removeAll();
    }

    private ItemStack getChainChestNether() {
        ItemStack item = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemMeta tm = item.getItemMeta();
        tm.setDisplayName(ChatColor.RED + "Chained OP sweatur");
        AttributeModifier mod = new AttributeModifier(UUID.randomUUID(), "generic.armor", 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier mod2 = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier mod3 = new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR, mod);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, mod2);
        tm.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, mod3);
        item.setItemMeta(tm);
        return item;
    }
    private ItemStack getBMIHoe() {
        ItemStack item = new ItemStack(Material.IRON_HOE);
        ItemMeta tm = item.getItemMeta();
        tm.setLore(Arrays.asList(ChatColor.GRAY+"Bone Mealed"));
        item.setItemMeta(tm);
        return item;
    }
    private ItemStack getBMDHoe() {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta tm = item.getItemMeta();
        tm.setLore(Arrays.asList(ChatColor.GRAY+"Bone Mealed"));
        item.setItemMeta(tm);
        return item;
    }
    private ItemStack getBMNHoe() {
        ItemStack item = new ItemStack(Material.NETHERITE_HOE);
        ItemMeta tm = item.getItemMeta();
        tm.setLore(Arrays.asList(ChatColor.GRAY+"Bone Mealed"));
        item.setItemMeta(tm);
        return item;
    }
    private ItemStack getCompressedBone() {
        ItemStack item = new ItemStack(Material.BONE_BLOCK);
        ItemMeta tm = item.getItemMeta();
        tm.setDisplayName(ChatColor.WHITE+"Compressed Bone Block");
        item.setItemMeta(tm);
        return item;
    }
    private ItemStack getGoldLegsNether() {
        ItemStack item = new ItemStack(Material.GOLDEN_LEGGINGS);
        ItemMeta tm = item.getItemMeta();
        tm.setDisplayName(ChatColor.RED + "guldn OP pantz");
        AttributeModifier mod = new AttributeModifier(UUID.randomUUID(), "generic.armor", 6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        AttributeModifier mod2 = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        AttributeModifier mod3 = new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR, mod);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, mod2);
        tm.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, mod3);
        item.setItemMeta(tm);
        return item;
    }
    private ItemStack getLethBootsNether() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta tm = item.getItemMeta();
        tm.setDisplayName(ChatColor.RED + "Lether OP shoez");
        AttributeModifier mod = new AttributeModifier(UUID.randomUUID(), "generic.armor", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        AttributeModifier mod2 = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        AttributeModifier mod3 = new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR, mod);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, mod2);
        tm.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, mod3);
        item.setItemMeta(tm);
        return item;
    }
    public ShapedRecipe getChainChestNetherRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "chain_nether_chestplate"), getChainChestNether());
        recipe.shape(
                "BBB",
                "BCB",
                "BBB"
        );
        recipe.setIngredient('B', Material.CHAIN);
        recipe.setIngredient('C', new RecipeChoice.MaterialChoice(Material.NETHERITE_CHESTPLATE));
        return recipe;
    }
    public ShapedRecipe getGoldLegsNetherRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "gold_nether_leggings"), getGoldLegsNether());
        recipe.shape(
                "BBB",
                "BCB",
                "BBB"
        );
        recipe.setIngredient('B', Material.GOLD_NUGGET);
        recipe.setIngredient('C', new RecipeChoice.MaterialChoice(Material.NETHERITE_LEGGINGS));
        return recipe;
    }
    public ShapedRecipe getLethBootsNetherRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "leather_nether_boots"), getLethBootsNether());
        recipe.shape(
                "BBB",
                "BCB",
                "BBB"
        );
        recipe.setIngredient('B', Material.LEATHER);
        recipe.setIngredient('C', new RecipeChoice.MaterialChoice(Material.NETHERITE_BOOTS));
        return recipe;
    }
    public ShapelessRecipe getGunPowderRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(this, "gun_powder"), new ItemStack(Material.GUNPOWDER, 2));
        recipe.addIngredient(Material.SAND);
        recipe.addIngredient(Material.BONE_MEAL);
        recipe.addIngredient(Material.CHARCOAL);
        return recipe;
    }
    public ShapedRecipe getEnderiumHelmetRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "enderium_helmet"), getEnderiteHelmet());
        recipe.shape(
                "NEN",
                "DHD",
                "   "
        );
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('E', new RecipeChoice.ExactChoice(getEnderiumIngot()));
        recipe.setIngredient('H', Material.NETHERITE_HELMET);
        return recipe;
    }
    public ShapedRecipe getEnderiumChestRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "enderium_chestplate"), getEnderiteChest());
        recipe.shape(
                "D D",
                "DED",
                "NCN"
        );
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('C', Material.NETHERITE_CHESTPLATE);
        recipe.setIngredient('E', new RecipeChoice.ExactChoice(getEnderiumIngot()));
        return recipe;
    }
    public ShapedRecipe getEnderiumLegRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "enderium_leggings"), getEnderiteLegs());
        recipe.shape(
                "NEN",
                "DLD",
                "D D"
        );
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('L', Material.NETHERITE_LEGGINGS);
        recipe.setIngredient('E', new RecipeChoice.ExactChoice(getEnderiumIngot()));
        return recipe;
    }
    public ShapedRecipe getEnderiumBootsRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "enderium_boots"), getEnderiteBoots());
        recipe.shape(
                "   ",
                "NBN",
                "DED"
        );
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.NETHERITE_BOOTS);
        recipe.setIngredient('E', new RecipeChoice.ExactChoice(getEnderiumIngot()));
        return recipe;
    }
    public ShapedRecipe getEnderiumIngotRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "enderium_ingot"), getEnderiumIngot());
        recipe.shape(
                "DED",
                "ONO",
                "DSD"
        );
        recipe.setIngredient('E', Material.ENDER_EYE);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('O', new RecipeChoice.ExactChoice(getReinforcedObi()));
        recipe.setIngredient('S', Material.END_STONE);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        return recipe;
    }
    public ShapedRecipe getEnderiumPickRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "enderium_pickaxe"), getEnderitePick());
        recipe.shape(
                "NEN",
                " P ",
                " S "
        );
        recipe.setIngredient('E', new RecipeChoice.ExactChoice(getEnderiumIngot()));
        recipe.setIngredient('P', Material.NETHERITE_PICKAXE);
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        return recipe;
    }
    public ShapedRecipe getEnderiumSwordRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "enderium_sword"), getEnderiteSword());
        recipe.shape(
                " E ",
                "NWN",
                " S "
        );
        recipe.setIngredient('E', new RecipeChoice.ExactChoice(getEnderiumIngot()));
        recipe.setIngredient('W', Material.NETHERITE_SWORD);
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        return recipe;
    }
    public ShapedRecipe getEnderiumAxeRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "enderium_axe"), getEnderiteAxe());
        recipe.shape(
                " NE",
                " AN",
                " S "
        );
        recipe.setIngredient('E', new RecipeChoice.ExactChoice(getEnderiumIngot()));
        recipe.setIngredient('A', Material.NETHERITE_AXE);
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        return recipe;
    }
    public ShapedRecipe getBMIHoeRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "iron_bone_mealed_hoe"), getBMIHoe());
        recipe.shape(
                "CCC",
                "CHC",
                "CCC"
        );
        recipe.setIngredient('C', new RecipeChoice.ExactChoice(getCompressedBone()));
        recipe.setIngredient('H', Material.IRON_HOE);
        return recipe;
    }
    public ShapedRecipe getBMDHoeRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "diamond_bone_mealed_hoe"), getBMDHoe());
        recipe.shape(
                "CCC",
                "CHC",
                "CCC"
        );
        recipe.setIngredient('C', new RecipeChoice.ExactChoice(getCompressedBone()));
        recipe.setIngredient('H', Material.DIAMOND_HOE);
        return recipe;
    }
    public ShapedRecipe getBMNHoeRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "netherite_bone_mealed_hoe"), getBMNHoe());
        recipe.shape(
                "CCC",
                "CHC",
                "CCC"
        );
        recipe.setIngredient('C', new RecipeChoice.ExactChoice(getCompressedBone()));
        recipe.setIngredient('H', Material.NETHERITE_HOE);
        return recipe;
    }
    public ShapedRecipe getCBBRecipe(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "compressed_bone_block"), getCompressedBone());
        recipe.shape(
                "CCC",
                "CCC",
                "CCC"
        );
        recipe.setIngredient('C', Material.BONE_BLOCK);
        return recipe;
    }
    public static ItemStack getEnderiumIngot(){
        ItemStack it = new ItemStack(Material.IRON_INGOT);
        ItemMeta tm = it.getItemMeta();
        tm.setDisplayName(ChatColor.LIGHT_PURPLE + "Enderium Ingot");
        it.setItemMeta(tm);
        return it;
    }
    public ItemStack getReinforcedObi(){
        ItemStack it = new ItemStack(Material.CRYING_OBSIDIAN);
        ItemMeta tm = it.getItemMeta();
        try {
            tm.setDisplayName(ChatColor.YELLOW + "Nethered Obsidian");
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
        it.setItemMeta(tm);
        return it;
    }
    public ItemStack getEnderiteHelmet(){
        ItemStack item = new ItemStack(Material.NETHERITE_HELMET);
        ItemMeta tm = item.getItemMeta();
        tm.setDisplayName(ChatColor.LIGHT_PURPLE + "Ender OP hat");
        AttributeModifier mod = new AttributeModifier(UUID.randomUUID(), "generic.armor", 6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        AttributeModifier mod2 = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 4.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        AttributeModifier mod3 = new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR, mod);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, mod2);
        tm.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, mod3);
        item.setItemMeta(tm);
        return item;
    }
    public ItemStack getEnderiteChest(){
        ItemStack item = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta tm = item.getItemMeta();
        tm.setDisplayName(ChatColor.LIGHT_PURPLE + "Ender OP sweatur");
        AttributeModifier mod = new AttributeModifier(UUID.randomUUID(), "generic.armor", 14.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier mod2 = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier mod3 = new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier mod4 = new AttributeModifier(UUID.randomUUID(), "generic.max_health", 10.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR, mod);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, mod2);
        tm.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, mod3);
        tm.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, mod4);
        item.setItemMeta(tm);
        return item;
    }
    public ItemStack getEnderiteLegs(){
        ItemStack item = new ItemStack(Material.NETHERITE_LEGGINGS);
        ItemMeta tm = item.getItemMeta();
        tm.setDisplayName(ChatColor.LIGHT_PURPLE + "Ender OP pantz");
        AttributeModifier mod = new AttributeModifier(UUID.randomUUID(), "generic.armor", 10.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        AttributeModifier mod2 = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 5.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        AttributeModifier mod3 = new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        AttributeModifier mod4 = new AttributeModifier(UUID.randomUUID(), "generic.movement_speed ", 0.05, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR, mod);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, mod2);
        tm.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, mod3);
        tm.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, mod4);
        item.setItemMeta(tm);
        return item;
    }
    public ItemStack getEnderiteBoots(){
        ItemStack item = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta tm = item.getItemMeta();
        tm.setDisplayName(ChatColor.LIGHT_PURPLE + "Ender OP shoez");
        AttributeModifier mod = new AttributeModifier(UUID.randomUUID(), "generic.armor", 6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        AttributeModifier mod2 = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 4.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        AttributeModifier mod3 = new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        AttributeModifier mod4 = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR, mod);
        tm.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, mod2);
        tm.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, mod3);
        tm.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, mod4);
        item.setItemMeta(tm);
        return item;
    }
    public ItemStack getEnderitePick(){
        ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta tm = item.getItemMeta();
        tm.setDisplayName(ChatColor.LIGHT_PURPLE + "Ender OP Pikax");
        AttributeModifier mod = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", 0.6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        tm.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, mod);
        item.setItemMeta(tm);
        return item;
    }
    public ItemStack getEnderiteSword(){
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta tm = item.getItemMeta();
        tm.setDisplayName(ChatColor.LIGHT_PURPLE + "Ender OP swurd");
        AttributeModifier mod = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", 0.8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier mod2 = new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier mod3 = new AttributeModifier(UUID.randomUUID(), "generic.attack_knockback", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        tm.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, mod);
        tm.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, mod2);
        tm.addAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK, mod3);
        item.setItemMeta(tm);
        return item;
    }
    public ItemStack getEnderiteAxe(){
        ItemStack item = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta tm = item.getItemMeta();
        tm.setDisplayName(ChatColor.LIGHT_PURPLE + "Ender OP ax");
        AttributeModifier mod = new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", 0.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier mod2 = new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", 12, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        tm.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, mod);
        tm.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, mod2);
        item.setItemMeta(tm);
        return item;
    }
}