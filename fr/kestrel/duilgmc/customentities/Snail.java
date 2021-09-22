package fr.kestrel.duilgmc.customentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.monster.EntitySilverfish;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class Snail extends EntitySilverfish implements Listener {
    public Snail(Location loc) {
        super(EntityTypes.aA, ((CraftWorld)loc.getWorld()).getHandle());
        this.getAttributeInstance(GenericAttributes.a).setValue(2.0D);
        this.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean("snail", true);
        this.save(nbt);
    }
    @Override
    protected void initPathfinder() {
        this.bP.a(1, new PathfinderGoalFloat(this));
        this.bP.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.bP.a(3, new PathfinderGoalRandomLookaround(this));
    }
    @Override
    protected SoundEffect getSoundAmbient() {
        return SoundEffects.rd;
    }
    @Override
    protected SoundEffect getSoundHurt(DamageSource damagesource) {
        return SoundEffects.rf;
    }
    @Override
    protected SoundEffect getSoundDeath() {
        return SoundEffects.qZ;
    }

    public void spawnEntity(){
        this.getWorld().addEntity(this);
    }

    @EventHandler
    public void died(EntityDeathEvent e){
        if(e.getEntity().getType() != EntityType.SILVERFISH) return;
        //NBTTagCompound nbt = e.getEntity()
    }
}
