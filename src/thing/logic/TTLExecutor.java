package thing.logic;

import arc.audio.*;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.logic.*;
import mindustry.logic.LExecutor.*;

public class TTLExecutor{
    public static class ShakeI implements LInstruction{
        public int intensity, x, y;
        
        public ShakeI(int intensity, int x, int y){
            this.intensity = intensity;
            this.x = x;
            this.y = y;
        }
        
        @Override
        public void run(LExecutor exec){
            Effect.shake(exec.numf(intensity), exec.numf(intensity), World.unconv(exec.numf(x)), World.unconv(exec.numf(y)));
        }
    }
    
    public static class PlaySoundI implements LInstruction{
        public int usePos, x, y, volume, pitch, sound;
        
        public PlaySoundI(int usePos, int x, int y, int volume, int pitch, int sound){
            this.usePos = usePos;
            this.x = x;
            this.y = y;
            this.volume = volume;
            this.pitch = pitch;
            this.sound = sound;
        }
        
        @Override
        public void run(LExecutor exec){
            if(exec.obj(sound) instanceof Sound sfx){
                if(exec.bool(usePos)){
                    sfx.at(World.unconv(exec.numf(x)), World.unconv(exec.numf(y)), exec.numf(pitch), exec.numf(volume));
                }else{
                    sfx.play(exec.numf(volume), exec.numf(pitch), 0f);
                }
            }
        }
    }
}
