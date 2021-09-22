package fr.kestrel.duilgmc.mineral;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class VanillaChunkGenerator extends ChunkGenerator {
    private static World world;

    public VanillaChunkGenerator(String world) {
        this.world = Bukkit.getWorld(world);
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList(new BlackOrePopulator());
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        int x = random.nextInt(250) - 250;
        int z = random.nextInt(250) - 250;
        int y = world.getHighestBlockYAt(x, z);
        return new Location(world, x, y, z);
    }
    public byte[] generate(World world, Random random, int i, int i1) {
        byte[] result = new byte[32768];
        return result;
    }
}
