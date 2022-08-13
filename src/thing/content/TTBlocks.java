package thing;

import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import thing.world.blocks.distribution.*;

import static mindustry.type.ItemStack.*;

public class TTBlocks{
    public static Block
    acceptor;
    
    public static void load(){
        acceptor = new Acceptor("acceptor"){{
            requirements(Category.distribution, with(Items.copper, 5, Items.lead, 5));
            size = 1;
            itemCapacity = 30;
        }};
    }
}
