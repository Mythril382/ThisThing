package thing.logic;

import arc.audio.*;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.logic.*;
import mindustry.logic.LExecutor.*;

public class TTLInstructions{
    public static class ShakeI implements LInstruction{
        public int intensity, x, y;
        
        public ShakeI(){
        }
        
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
        
        public PlaySoundI(){
        }
        
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
    
    public static class RandI implements LInstruction{
        public LRand method = LRand.nextDouble;
        public int p1, p2, result;
        
        public RandI(){
        }
        
        public RandI(LRand method, int p1, int p2, int result){
            this.method = method;
            this.p1 = p1;
            this.p2 = p2;
            this.result = result;
        }
        
        @Override
        public void run(LExecutor exec){
            if(method.func1 != null){
                exec.setnum(result, method.func1.get(exec.num(p1)));
            }else if(method.func2 != null){
                exec.setnum(result, method.func2.get(exec.num(p1), exec.num(p2)));
            }
        }
    }
}
