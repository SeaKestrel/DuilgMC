package fr.kestrel.duilgmc.patched;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class DisableEntities implements Listener {

    @EventHandler
    public void spawn(EntitySpawnEvent e){
        if (e.getEntity() instanceof LivingEntity){
            if(e.getEntity() instanceof Squid || e.getEntity() instanceof Salmon || e.getEntity() instanceof Cod || e.getEntity() instanceof PufferFish || e.getEntity() instanceof TropicalFish || e.getEntity() instanceof Salmon){
                ((LivingEntity) e.getEntity()).setAI(false);
            }
        }
    }

}
