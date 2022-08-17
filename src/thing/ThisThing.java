package thing;

import arc.*;
import arc.func.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.logic.*;
import mindustry.mod.*;
import thing.content.*;
import thing.logic.*;

public class ThisThing extends Mod{
    public ThisThing(){
        Events.on(ClientLoadEvent.class, e -> {
            TTLStatements.load();
        });
    }
    
    @Override
    public void loadContent(){
        TTBlocks.load();
    }
    
    /** Mimics the RegisterStatement annotation. Credits to Deltanedas for creating the original function.
     * @param name The name of the statement to be registered.
     * @param func The Func to be added to the custom parsers list.
     * @param prov The Prov to be added to the all statements list.
    */
    public static void registerStatement(String name, Func<String[], LStatement> func, Prov<LStatement> prov){
        LAssembler.customParsers.put(name, func);
        LogicIO.allStatements.add(prov);
    }
}
