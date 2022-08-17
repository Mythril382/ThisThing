package thing.logic;

import mindustry.core.*;
import mindustry.entities.*;
import mindustry.logic.*;
import mindustry.logic.LExecutor.*;

public class TTLExecutor{
    public static class ShakeI implements LInstruction{
        public int duration, x, y;
        
        public ShakeI(int duration, int x, int y){
            this.duration = duration;
            this.x = x;
            this.y = y;
        }
        
        @Override
        public void run(LExecutor exec){
            Effect.shake(exec.numf(duration), exec.numf(duration), World.unconv(exec.numf(x)), World.unconv(exec.numf(y)));
        }
    }
}
