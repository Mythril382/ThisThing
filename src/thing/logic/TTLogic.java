package thing.logic;

/* import arc.audio.*;
import arc.files.*; */
import arc.graphics.*;
import arc.struct.*;
// import arc.util.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;

import java.lang.reflect.*;

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
        
        // im not typing in every preset color manually
        Field[] fieldsArr = Color.class.getFields();
        Seq<Fields> fields = new Seq<>(fields);
        fields = fields.select(f -> Color.class.isAssignableFrom(f.getType()));
        fields.each(f -> {
            Color col = f.get(Color.class);
            logicVars.put("@col" + f.getName(), Color.toDoubleBits(col.r, col.g, col.b, col.a));
        });
    }
}
