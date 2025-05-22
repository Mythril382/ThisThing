package thing.logic;

import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;

import static mindustry.Vars.*;

public class TTLogic{
    public static final LCategory
    categoryMisc = new LCategory("misc", Pal.heal.cpy().mul(0.75f), Icon.effectSmall);
    
    public static void load(){
        TTLStatements.load();
    }
}
