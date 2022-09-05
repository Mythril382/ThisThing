package thing.logic;

/* import arc.audio.*;
import arc.files.*;
import arc.struct.*;
import arc.util.*; */
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class TTLogic{
    public static final LCategory
    categoryMisc = new LCategory("misc", Pal.heal.cpy().mul(0.75f), Icon.effectSmall);
    
    public static void load(){
        TTLStatements.load();
        
        /* IntMap idToSound = (IntMap)Reflect.get(Sounds.class, "idToSound");
        for(int i = 0; i < idToSound.size; i++){
            if(i != Sounds.getSoundId(Sounds.swish)){
                Sound sound = Sounds.getSound(i);
                Fi file = (Fi)Reflect.get(sound, "file");
                String name = file.nameWithoutExtension();
                logicVars.put("@sfx" + name, sound);
            }
        } */
        
        // env enums
        logicVars.put("@envTerrestrial", Env.terrestrial);
        logicVars.put("@envSpace", Env.space);
        logicVars.put("@envUnderwater", Env.underwater);
        logicVars.put("@envSpores", Env.spores);
        logicVars.put("@envScorching", Env.scorching);
        logicVars.put("@envGroundOil", Env.groundOil);
        logicVars.put("@envGroundWater", Env.groundWater);
        logicVars.put("@envOxygen", Env.oxygen);
        logicVars.put("@envAny", Env.any);
        logicVars.put("@envNone", Env.none);
    }
}
