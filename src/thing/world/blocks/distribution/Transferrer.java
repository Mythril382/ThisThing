package thing.world.blocks.distribution;

import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import thing.world.blocks.distribution.Acceptor.*;

import static mindustry.Vars.*;

public class Transferrer extends Block{
    public final int timerTransfer = timers++;
    /** Transfer range. */
    public float range = 60f;
    /** Transfer cooldown. */
    public float transferTime = 1f;
    
    public Transferrer(String name){
        super(name);

        acceptsItems = true;
        hasItems = true;
        separateItemCapacity = true;
        underBullets = true;
        update = true;
        solid = false;
        group = BlockGroup.transportation;
        priority = TargetPriority.transport;
    }
    
    @Override
    public void setStats(){
        super.setStats();
        
        stats.add(Stat.range, range / tilesize, StatUnit.blocks);
        stats.add(Stat.itemsMoved, 60f / transferTime, StatUnit.itemsSecond);
    }
    
    @Override
    public boolean outputsItems(){
        return false;
    }
    
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.accent);
        Building acceptor = Units.closestBuilding(player.team(), x * tilesize + offset, y * tilesize + offset, range, b -> b instanceof AcceptorBuild);
        if(acceptor != null) Drawf.square(acceptor.x, acceptor.y, acceptor.block.size * tilesize / 2f + 2f, Pal.place);
    }
    
    public class TransferrerBuild extends Building{
        @Override
        public void updateTile(){
            if(items.any() && timer(timerTransfer, transferTime)){
                Building acceptor = Units.closestBuilding(team, x, y, range, b -> b instanceof AcceptorBuild && b.items.get(items.first()) < b.getMaximumAccepted(items.first()));
                if(acceptor != null){
                    Fx.itemTransfer.at(x, y, 1f, items.first().color, acceptor);
                    acceptor.handleItem(this, items.first());
                    items.remove(items.first(), 1);
                }
            }
        }
        
        @Override
        public boolean acceptItem(Building source, Item item){
            return items.get(item) < getMaximumAccepted(item);
        }
        
        @Override
        public void drawSelect(){
            Drawf.dashCircle(x, y, range, Pal.accent);
            if(!items.any()) return;
            Building acceptor = Units.closestBuilding(team, x, y, range, b -> b instanceof AcceptorBuild && b.items.get(items.first()) < b.getMaximumAccepted(items.first()));
            if(acceptor != null) Drawf.square(acceptor.x, acceptor.y, acceptor.block.size * tilesize / 2f + 2f, Pal.place);
        }
    }
}
