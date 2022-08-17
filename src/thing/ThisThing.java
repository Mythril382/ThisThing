package thing;

import arc.*;
import arc.audio.*;
import arc.files.*;
import arc.struct.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import thing.content.*;
import thing.logic.*;

import java.lang.reflect.*;

import static mindustry.Vars.*;

public class ThisThing extends Mod{
    public ThisThing(){
        Events.on(ClientLoadEvent.class, e -> {
            try{
                Field f = Sounds.class.getDeclaredField("idToSound");
                f.setAccessible(true);
                IntMap idToSound = (IntMap)f.get(Sounds.class);
                for(int i = 0; i < idToSound.size; i++){
                    // swish can breaks game audio sooooooo
                    if(i == Sounds.getSoundId(Sounds.swish)) return;
                    Sound sound = Sounds.getSound(i);
                    try{
                        Field f2 = sound.getClass().getDeclaredField("file");
                        f2.setAccessible(true);
                        Fi file = (Fi)f2.get(sound);
                        String name = file.nameWithoutExtension();
                        logicVars.put("@sfx" + name, sound);
                    }catch(Throwable ignoreAgain){}
                }
            }catch(Throwable ignore){}
        });
    }
    
    @Override
    public void loadContent(){
        TTBlocks.load();
        TTLStatements.load();
    }
}
