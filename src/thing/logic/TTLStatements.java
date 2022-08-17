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
            builder
                .append("shake ")
                .append(intensity)
                .append(" ")
                .append(x)
                .append(" ")
                .append(y);
        }
    }
    
    public static class PlaySoundStatement extends LStatement{
        public String usePos = "true", x = "0", y = "0", volume = "1", pitch = "1", sound = "@sfxpew";
        
        public PlaySoundStatement(){
        }
        
        public PlaySoundStatement(String usePos, String x, String y, String volume, String pitch, String sound){
            this.usePos = usePos;
            this.x = x;
            this.y = y;
            this.volume = volume;
            this.pitch = pitch;
            this.sound = sound;
        }
        
        @Override
        public void build(Table table){
            fields(table, "usePos", usePos, str -> usePos = str);
            row(table);
            fields(table, "x", x, str -> x = str);
            fields(table, "y", y, str -> y = str);
            row(table);
            fields(table, "volume", volume, str -> volume = str);
            fields(table, "pitch", pitch, str -> pitch = str);
            row(table);
            fields(table, "sound", sound, str -> sound = str);
        }
        
        @Override
        public boolean privileged(){
            return true;
        }
        
        @Override
        public LInstruction build(LAssembler b){
            return new PlaySoundI(b.var(usePos), b.var(x), b.var(y), b.var(volume), b.var(pitch), b.var(sound));
        }
        
        @Override
        public LCategory category(){
            return LCategory.world;
        }
        
        @Override
        public void write(StringBuilder builder){
            builder
                .append("playsound ")
                .append(usePos)
                .append(" ")
                .append(x)
                .append(" ")
                .append(y)
                .append(" ")
                .append(volume)
                .append(" ")
                .append(pitch)
                .append(" ")
                .append(sound);
        }
    }
    
    public static void load(){
        registerStatement("shake", args -> new ShakeStatement(args[1], args[2], args[3]), ShakeStatement::new);
        registerStatement("playsound", args -> new PlaySoundStatement(args[1], args[2], args[3], args[4], args[5], args[6]), PlaySoundStatement::new);
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
