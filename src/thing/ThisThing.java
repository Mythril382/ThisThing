package thing;

import arc.*;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
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
    }
}
