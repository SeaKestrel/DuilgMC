package fr.kestrel.duilgmc.events;

import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TurnBlock implements Listener {

    @EventHandler
    public void turnBlock(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getItem() == null) return;
        if(e.getItem().getType() != Material.PAPER) return;
        if(!e.getItem().hasItemMeta()) return;
        if(!e.getItem().getItemMeta().hasCustomModelData()) return;
        if(e.getItem().getItemMeta().getCustomModelData() != 12) return;
        BlockData data = e.getClickedBlock().getBlockData();
        if(data == null) return;
        if(data instanceof Orientable){
            Orientable bD = (Orientable) data;
            switch (bD.getAxis()) {
                case X -> {
                    if (bD.getAxes().contains(Axis.Y)) bD.setAxis(Axis.Y);
                    else if (bD.getAxes().contains(Axis.Z)) bD.setAxis(Axis.Z);
                    else if (bD.getAxes().contains(Axis.X)) bD.setAxis(Axis.X);
                    e.getClickedBlock().setBlockData(bD, false);
                }
                case Y -> {
                    if (bD.getAxes().contains(Axis.Z)) bD.setAxis(Axis.Z);
                    else if (bD.getAxes().contains(Axis.X)) bD.setAxis(Axis.X);
                    else if (bD.getAxes().contains(Axis.Y)) bD.setAxis(Axis.Y);
                    e.getClickedBlock().setBlockData(bD, false);
                }
                case Z -> {
                    if (bD.getAxes().contains(Axis.X)) bD.setAxis(Axis.X);
                    else if (bD.getAxes().contains(Axis.Y)) bD.setAxis(Axis.Y);
                    else if (bD.getAxes().contains(Axis.Z)) bD.setAxis(Axis.Z);
                    e.getClickedBlock().setBlockData(bD, false);
                }
            }
        } else if (data instanceof Directional){
            Directional bD = (Directional) data;
        }
    }
}
