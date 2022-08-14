package thing.content;

import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import thing.world.blocks.distribution.*;

import static mindustry.type.ItemStack.*;

public class TTBlocks{
    public static Block
    acceptor, transferrer, burstTransferrer;
    
    public static void load(){
        acceptor = new Acceptor("acceptor"){{
            requirements(Category.distribution, with(Items.copper, 8, Items.lead, 6));
            health = 50;
            size = 1;
            itemCapacity = 50;
        }};
        
        transferrer = new Transferrer("transferrer"){{
            requirements(Category.distribution, with(Items.copper, 15, Items.lead, 6));
            health = 60;
            size = 1;
            itemCapacity = 15;
            range = 48f;
            clipSize = range;
            transferTime = 10f;
        }};
        
        burstTransferrer = new Transferrer("burst-transferrer"){{
            requirements(Category.distribution, with(Items.titanium, 8, Items.copper, 20, Items.lead, 16));
            health = 80;
            size = 1;
            itemCapacity = 20;
            range = 96f;
            clipSize = range;
            transferTime = 4.25f;
        }};
    }
}
