package fr.kestrel.duilgmc.events;

import fr.kestrel.duilgmc.mineral.BlackOrePopulator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class WorldInit implements Listener {

    @EventHandler
    public void wie(WorldInitEvent e){
        e.getWorld().getPopulators().add(new BlackOrePopulator());
    }

}
