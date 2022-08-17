package thing;

import mindustry.mod.*;
import thing.content.*;
import thing.logic.*;

public class ThisThing extends Mod{
    @Override
    public void loadContent(){
        TTBlocks.load();
        TTLStatements.load();
    }
}
