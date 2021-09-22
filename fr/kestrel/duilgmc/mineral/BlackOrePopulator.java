package fr.kestrel.duilgmc.mineral;

import fr.kestrel.duilgmc.Main;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class BlackOrePopulator extends BlockPopulator {
    private static final int ORE_CHANCE = 1;
    private static final int DOUBLE_ORE_CHANCE = 1;

    public void populate(World world, Random random, Chunk source){
        int x = random.nextInt(14) + 1;
        int z = random.nextInt(14) + 1;
        int y = 8 - random.nextInt(4);
        source.getBlock(x,y,z).setType(Material.OBSIDIAN);
        Main.addBlackOre(new Location(world, x, y ,z).serialize().toString());
        if(random.nextInt(5) <= DOUBLE_ORE_CHANCE){
            int dir = random.nextInt(4);
            if (dir == 0) {
                source.getBlock(x+1,y+1,z).setType(Material.OBSIDIAN);
                Main.addBlackOre(new Location(world, x, y ,z).serialize().toString());
                System.out.println(new Location(world, x, y ,z).serialize().toString());
            } else if (dir == 1) {
                source.getBlock(x-1,y+1,z).setType(Material.OBSIDIAN);
                Main.addBlackOre(new Location(world, x, y ,z).serialize().toString());
                System.out.println(new Location(world, x, y ,z).serialize().toString());
            } else if (dir == 2) {
                source.getBlock(x,y+1,z+1).setType(Material.OBSIDIAN);
                Main.addBlackOre(new Location(world, x, y ,z).serialize().toString());
                System.out.println(new Location(world, x, y ,z).serialize().toString());
            } else {
                source.getBlock(x,y+1,z-1).setType(Material.OBSIDIAN);
                Main.addBlackOre(new Location(world, x, y ,z).serialize().toString());
                System.out.println(new Location(world, x, y ,z).serialize().toString());
            }
        }
    }
}
