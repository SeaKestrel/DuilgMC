package fr.kestrel.duilgmc.skills;

import org.bukkit.Material;

public enum SkillType {
    FORAGER(Material.NETHERITE_AXE, "forager"),
    MINER(Material.NETHERITE_PICKAXE, "miner"),
    HUNTER(Material.NETHERITE_SWORD, "hunter"),
    FARMER(Material.NETHERITE_HOE, "farmer");

    Material item;
    String id;

    SkillType(Material item, String id){
        this.item = item;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Material getItem() {
        return item;
    }
}
