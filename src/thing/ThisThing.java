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
            
            Time.runTask(6f, () -> {
                DatabaseDialog db = ui.database;
                Table all = (Table)Reflect.get(db, "all");
                db.resizedShown(() -> {
                    all.add("@content.lst.name").growX().left().color(Pal.accent);
                    all.row();
                    all.image().growX().pad(5).padLeft(0).padRight(0).height(3).color(Pal.accent);
                    all.row();
                    all.table(list -> {
                        list.left();
                        int cols = (int)Mathf.clamp((graphics.getWidth() - Scl.scl(30)) / Scl.scl(32 + 12), 1, 22);
                        int count = 0;
                        for(int i = 0; i < LogicIO.allStatements.size; i++){
                            LStatement lst = LogicIO.allStatements.get(i).get();
                            if(!(lst instanceof InvalidStatement)){
                                Image image = new Image(atlas.find("this-thing-lst-icon"));
                                list.add(image).size(8 * 4).pad(3);
                                image.clicked(() -> {
                                    ui.showInfo("[accent]Name[]: " + lst.name() + "\n[accent]Category[]: " + "[#" lst.category().color "]" + lst.category().localized() + "[]");
                                });
                                image.addListener(new Tooltip(t -> t.setBackground(Tex.button).add("[#" + lst.category().color + "]" + lst.name())));
                            }
                        }
                    });
                    all.row();
                });
            });
        });
    }
    
    @Override
    public void loadContent(){
        TTBlocks.load();
        TTLStatements.load();
    }
}
