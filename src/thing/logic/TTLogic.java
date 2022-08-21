package thing.logic;

import arc.audio.*;
import arc.files.*;
import arc.struct.*;
import arc.util.*;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class TTLogic{
    public static void load(){
        TTLStatements.load();
        
        IntMap idToSound = (IntMap)Reflect.get(Sounds.class, "idToSound");
        for(int i = 0; i < idToSound.size; i++){
            // swish can break game audio sooooooo
            if(i != Sounds.getSoundId(Sounds.swish)){
                Sound sound = Sounds.getSound(i);
                Fi file = (Fi)Reflect.get(sound, "file");
                String name = file.nameWithoutExtension();
                logicVars.put("@sfx" + name, sound);
            }
        }
    }
}
