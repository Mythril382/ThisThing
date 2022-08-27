package thing;

import arc.struct.*;
import mindustry.ai.*;
import mindustry.mod.*;
import thing.ai.*;
import thing.content.*;
import thing.logic.*;

import static mindustry.Vars.*;

public class ThisThing extends Mod{
    @Override
    public void loadContent(){
        TTBlocks.load();
        TTLogic.load();
        
        content.units().each(unit -> {
            Seq<UnitCommand> cmds = Seq.with(unit.commands);
            cmds.add(UCommands.patrolCommand);
            unit.commands = cmds.toArray(UnitCommand.class);
        });
    }
}
