package thing.logic;

import arc.func.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.logic.*;
import mindustry.logic.LExecutor.*;
import mindustry.ui.*;
import thing.logic.TTLInstructions.*;

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
    
    public static class RandStatement extends LStatement{
        public LRand method = LRand.nextDouble;
        public String p1 = "a", p2 = "b", result = "result";
        
        public RandStatement(){
        }
        
        public RandStatement(String method, String p1, String p2, String result){
            try{
                this.method = LRand.valueOf(method);
            }catch(Throwable ignored){}
            this.p1 = p1;
            this.p2 = p2;
            this.result = result;
        }
        
        @Override
        public void build(Table table){
            rebuild(table);
        }
        
        void rebuild(Table table){
            table.clearChildren();
            fields(table, result, str -> result = str);
            table.add("=");
            table.button(b -> {
                b.label(() -> method.name());
                b.clicked(() -> showSelect(b, LRand.all, method, o -> {
                    method = o;
                    rebuild(table);
                }, 2, c -> c.size(120f, 40f)));
            }, Styles.logict, () -> {}).size(120f, 40f).pad(4f).color(table.color);
            row(table);
            fields(table, p1, str -> p1 = str);
            if(method.func2 != null) fields(table, p2, str -> p2 = str);
        }
        
        @Override
        public LInstruction build(LAssembler b){
            return new RandI(method, b.var(p1), b.var(p2), b.var(result));
        }
        
        @Override
        public LCategory category(){
            return LCategory.operation;
        }
        
        @Override
        public void write(StringBuilder builder){
            builder
                .append("rand ")
                .append(method.name())
                .append(" ")
                .append(p1)
                .append(" ")
                .append(p2)
                .append(" ")
                .append(result);
        }
    }
    
    public static class StringOpStatement extends LStatement{
        public LStringOp op = LStringOp.charAt;
        public String string = "\"string\"", p1 = "1", result = "result";
        
        public StringOpStatement(){
        }
        
        public StringOpStatement(String op, String string, String p1, String result){
            try{
                this.op = LStringOp.valueOf(op);
            }catch(Throwable ignored){}
            this.string = string;
            this.p1 = p1;
            this.result = result;
        }
        
        @Override
        public void build(Table table){
            rebuild(table);
        }
        
        void rebuild(Table table){
            table.clearChildren();
            fields(table, result, str -> result = str);
            table.add("=");
            table.button(b -> {
                b.label(() -> op.name());
                b.clicked(() -> showSelect(b, LStringOp.all, op, o -> {
                    op = o;
                    rebuild(table);
                }, 2, c -> c.size(120f, 40f)));
            }, Styles.logict, () -> {}).size(120f, 40f).pad(4f).color(table.color);
            row(table);
            fields(table, string, str -> string = str);
            // i know i could just do this with an if statement but why not
            switch(op){
                case charAt, concat -> fields(table, p1, str -> p1 = str);
            }
        }
        
        @Override
        public LInstruction build(LAssembler b){
            return new StringOpI(op, b.var(string), b.var(p1), b.var(result));
        }
        
        @Override
        public LCategory category(){
            return LCategory.operation;
        }
        
        
        @Override
        public void write(StringBuilder builder){
            builder
                .append("stringop ")
                .append(op.name())
                .append(" ")
                .append(string)
                .append(" ")
                .append(p1)
                .append(" ")
                .append(result);
        }
    }
    
    public static void load(){
        registerStatement("shake", args -> new ShakeStatement(args[1], args[2], args[3]), ShakeStatement::new);
        registerStatement("playsound", args -> new PlaySoundStatement(args[1], args[2], args[3], args[4], args[5], args[6]), PlaySoundStatement::new);
        registerStatement("rand", args -> new RandStatement(args[1], args[2], args[3], args[4]), RandStatement::new);
        registerStatement("stringop", args -> new StringOpStatement(args[1], args[2], args[3], args[4]), StringOpStatement::new);
    }
    
    /** Mimics the RegisterStatement annotation.
     * @param name The name of the statement to be registered.
     * @param func The statement function. Used for reading saves.
     * @param prov The statement provider. Used for examples.
    */
    public static void registerStatement(String name, Func<String[], LStatement> func, Prov<LStatement> prov){
        LAssembler.customParsers.put(name, func);
        LogicIO.allStatements.add(prov);
    }
}
