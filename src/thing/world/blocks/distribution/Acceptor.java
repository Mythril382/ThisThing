package thing.world.blocks.distribution;

import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class Acceptor extends Block{
    public Acceptor(String name){
        super(name);
        hasItems = true;
        separateItemCapacity = true;
        underBullets = true;
        update = true;
        solid = false;
        group = BlockGroup.transportation;
        priority = TargetPriority.transport;
    }
    
    @Override
    public boolean outputsItems(){
        return true;
    }
    
    public class AcceptorBuild extends Building{
        @Override
        public void updateTile(){
            if(timer(timerDump, dumpTime)){
                dump();
            }
        }
        
        @Override
        public boolean acceptItem(Building source, Item item){
            return items.get(item) < getMaximumAccepted(item);
        }
    }
}
