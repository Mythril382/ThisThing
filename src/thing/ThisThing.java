package thing;

import arc.*;
import arc.graphics.g2d.*;
// import arc.struct.*;
import arc.util.*;
// import mindustry.ai.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
// import thing.ai.*;
import thing.logic.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class ThisThing extends Mod{
    public static TextureRegion arrival;
    
    public ThisThing(){
        Events.on(ClientLoadEvent.class, e -> {
            TextureRegion[] arrivals = new TextureRegion[8];
            for(int i = 0; i <= 7; i++){
                arrivals[i] = atlas.find("this-thing-arrival" + i);
            }
            arrival = arrivals[0];
            Events.run(Trigger.update, () -> {
                int arrivalFrame = (int)((Time.globalTime / 5f) % arrivals.length);
                arrival.set(arrivals[arrivalFrame]);
            });
        });
    }
    
    @Override
    public void loadContent(){
        TTLogic.load();
        
        /* broken atm, PatrolAI stays inaccessible
        content.units().each(unit -> {
            Seq<UnitCommand> cmds = Seq.with(unit.commands);
            cmds.add(UCommands.patrolCommand);
            unit.commands = cmds.toArray(UnitCommand.class);
        });
        */
    }
}
