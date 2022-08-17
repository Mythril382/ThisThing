package thing.logic;

import arc.func.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.logic.*;
import mindustry.logic.LExecutor.*;
import thing.*;

import static thing.logic.TTLExecutor.*;

public class TTLStatements{
    public static class ShakeStatement extends LStatement{
        public String intensity = "10", x = "0", y = "0";
        
        public ShakeStatement(){
        }
        
        public ShakeStatement(String intensity, String x, String y){
            this.intensity = intensity;
            this.x = x;
            this.y = y;
        }
        
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
        
        @Override
        public void write(StringBuilder builder){
            builder.append("shake ").append(intensity).append(" ").append(x).append(" ").append(y);
        }
    }
    
    public static void load(){
        registerStatement("shake", args -> new ShakeStatement(args[1], args[2], args[3]), ShakeStatement::new);
    }
    
    /** Mimics the RegisterStatement annotation.
     * @param name The name of the statement to be registered.
     * @param func The Func to be added to the custom parsers list.
     * @param prov The Prov to be added to the all statements list.
    */
    public static void registerStatement(String name, Func<String[], LStatement> func, Prov<LStatement> prov){
        LAssembler.customParsers.put(name, func);
        LogicIO.allStatements.add(prov);
    }
}
