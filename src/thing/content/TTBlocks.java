package thing.content;

import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import thing.world.blocks.distribution.*;

import static mindustry.type.ItemStack.*;

public class TTBlocks{
    public static Block
    acceptor, transferrer;
    
    public static void load(){
        acceptor = new Acceptor("acceptor"){{
            requirements(Category.distribution, with(Items.copper, 8, Items.lead, 6));
            size = 1;
            itemCapacity = 30;
        }};
        
        transferrer = new Transferrer("transferrer"){{
            requirements(Category.distribution, with(Items.copper, 15, Items.lead, 6));
            size = 1;
            itemCapacity = 15;
            range = 48f;
            transferTime = 10f;
        }};
    }
}
