package fr.kestrel.duilgmc.customentities;

import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.level.pathfinder.PathEntity;
import org.bukkit.Location;

import java.util.EnumSet;

public class PathfinderGoalRandomWalkaround extends PathfinderGoal {
    private final EntityInsentient a;
    private double b;
    private double c;
    private int d;

    public PathfinderGoalRandomWalkaround(EntityInsentient var0) {
        this.a = var0;
        this.a(EnumSet.of(Type.a, Type.b));
    }

    public boolean a() {
        return true;
    }

    public void c() {
        double var0 = 6.283185307179586D * this.a.getRandom().nextDouble();
        this.b = Math.cos(var0);
        this.c = Math.sin(var0);
        this.d = 20 + this.a.getRandom().nextInt(20);
    }
}
