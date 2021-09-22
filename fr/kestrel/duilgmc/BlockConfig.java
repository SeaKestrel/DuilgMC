package fr.kestrel.duilgmc;

import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import java.util.ArrayList;
import java.util.List;

public class BlockConfig implements Listener {


    public static List<Block> blocks = new ArrayList<>();

    public BlockConfig(){
    }

    public List<Block> getBlocks(){
        return blocks;
    }


}
