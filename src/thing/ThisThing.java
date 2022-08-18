package thing;

import arc.*;
import arc.audio.*;
import arc.files.*;
import arc.scene.style.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.*;
import thing.content.*;
import thing.logic.*;

import java.lang.reflect.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class ThisThing extends Mod{
    public ThisThing(){
        Events.on(ClientLoadEvent.class, e -> {
            try{
                Field f = Sounds.class.getDeclaredField("idToSound");
                f.setAccessible(true);
                IntMap idToSound = (IntMap)f.get(Sounds.class);
                for(int i = 0; i < idToSound.size; i++){
                    // swish can break game audio sooooooo
                    if(i != Sounds.getSoundId(Sounds.swish)){
                        Sound sound = Sounds.getSound(i);
                        try{
                            Field f2 = sound.getClass().getDeclaredField("file");
                            f2.setAccessible(true);
                            Fi file = (Fi)f2.get(sound);
                            String name = file.nameWithoutExtension();
                            logicVars.put("@sfx" + name, sound);
                        }catch(Throwable ignoreAgain){}
                    }
                }
            }catch(Throwable ignore){}
            
            Time.runTask(6f, () -> {
                if(mobile) mobileButton();
                else desktopButton();
                Events.on(ResizeEvent.class, () -> {
                    if(mobile) mobileButton();
                    else desktopButton();
                });
            });
        });
    }
    
    @Override
    public void loadContent(){
        TTBlocks.load();
        TTLStatements.load();
    }
    
    public static void mobileButton(){
        Table buttons = ui.menuGroup.<Table>find("buttons");
        
        // Credits to Liplum (Creator of CyberIO) for this piece of code.
        boolean row = buttons.getChildren().count(b -> b instanceof MobileButton) % (graphics.isPortrait() ? 2 : 4) == 0;
        if(row) buttons.row();
        
        buttons.add(new MobileButton(new TextureRegionDrawable(atlas.find("this-thing-button")), bundle.get("this-thing-button.name"), () -> {}));
    }
    
    public static void desktopButton(){
    }
}
