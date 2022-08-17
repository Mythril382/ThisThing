package thing.logic;

import mindustry.core.*;
import mindustry.entities.*;
import mindustry.logic.*;
import mindustry.logic.LExecutor.*;

public class TTLExecutor{
    public static class ShakeI implements LInstruction{
        public int intensity, x, y;
        
        public ShakeI(int duration, int x, int y){
            this.intensity = intensity;
            this.x = x;
            this.y = y;
        }
        
        @Override
        public void run(LExecutor exec){
            Effect.shake(exec.numf(intensity), exec.numf(intensity), World.unconv(exec.numf(x)), World.unconv(exec.numf(y)));
        }
    }
}
