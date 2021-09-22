package fr.kestrel.duilgmc.netheritegolem;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalUniversalAngerReset;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

public class NetheriteGolem extends EntityIronGolem {
    public NetheriteGolem(Location l) {
        super(EntityTypes.P, ((CraftWorld)l.getWorld()).getHandle());
        this.setPosition(l.getX(), l.getY(), l.getZ());
        this.initPathfinder();
        this.setCustomName(IChatBaseComponent.a("Le boss des boss !"));
        this.setCustomNameVisible(true);
    }

    public Location getLocation(){
        return this.getBukkitEntity().getLocation();
    }

    @Override
    protected void initPathfinder(){
        this.bP.a(1, new PathfinderGoalMeleeAttack(this, 1.5D, true));
        this.bP.a(2, new PathfinderGoalMoveTowardsTarget(this, 1.5D, 32.0F));
        this.bP.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.bP.a(8, new PathfinderGoalRandomLookaround(this));
        this.bP.a(2, new PathfinderGoalHurtByTarget(this, new Class[0]));
        this.bP.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, false));
        this.bP.a(4, new PathfinderGoalUniversalAngerReset<>(this, false));
    }

    public void setNoAI(boolean ai){
        this.setNoAI(ai);
    }
}
