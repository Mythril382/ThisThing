package thing.logic;

import arc.scene.ui.layout.*;
import mindustry.logic.*;
import mindustry.logic.LExecutor.*;
import thing.*;

import static thing.logic.TTLExecutor.*;

public class TTLStatements{
    public static class ShakeStatement extends LStatement{
        public String intensity = "10", x = "0", y = "0";
        
        @Override
        public void build(Table table){
            fields(table, "intensity", intensity, str -> intensity = str);
            row(table);
            fields(table, "x", x, str -> x = str);
            row(table);
            fields(table, "y", y, str -> y = str);
        }
        
        @Override
        public boolean privileged(){
            return true;
        }
        
        @Override
        public LInstruction build(LAssembler b){
            return new ShakeI(b.var(intensity), b.var(x), b.var(y));
        }
        
        @Override
        public LCategory category(){
            return LCategory.world;
        }
    }
    ThisThing.registerStatement("shake", strArr -> new ShakeStatement(), () -> new ShakeStatement());
}
