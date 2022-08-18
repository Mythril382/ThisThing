package thing;

import arc.*;
import arc.audio.*;
import arc.files.*;
import arc.math.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.logic.LStatements.*;
import mindustry.mod.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import thing.content.*;
import thing.logic.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class ThisThing extends Mod{
    public ThisThing(){
        Events.on(ClientLoadEvent.class, e -> {
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
        });
    }
    
    @Override
    public void loadContent(){
        TTBlocks.load();
        TTLStatements.load();
    }
}
