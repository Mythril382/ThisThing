package thing.logic;

import arc.func.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.logic.*;
import mindustry.logic.LExecutor.*;
import mindustry.ui.*;
import thing.*;
import thing.logic.TTLInstructions.*;

public class TTLStatements{
    // IO
    
    public static class ReadMessageStatement extends LStatement{
        public String building = "message1", result = "result";
        
        public ReadMessageStatement(){
        }
        
        public ReadMessageStatement(String building, String result){
            this.building = building;
            this.result = result;
        }
        
        @Override
        public void build(Table table){
            fields(table, result, str -> result = str);
            table.add(" = message of ");
            fields(table, building, str -> building = str);
        }
        
        @Override
        public LInstruction build(LAssembler b){
            return new ReadMessageI(b.var(building), b.var(result));
        }
        
        @Override
        public LCategory category(){
            return LCategory.io;
        }
        
        @Override
        public void write(StringBuilder builder){
            builder
                .append("readmessage ")
                .append(building)
                .append(" ")
                .append(result);
        }
    }
    
    // Operation
    
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
            table.add(" = ");
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
            table.add(" = ");
            table.button(b -> {
                b.label(() -> op.name());
                b.clicked(() -> showSelect(b, LStringOp.all, op, o -> {
                    op = o;
                    rebuild(table);
                }, 2, c -> c.size(150f, 40f)));
            }, Styles.logict, () -> {}).size(150f, 40f).pad(4f).color(table.color);
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
    
    public static class ColorOpStatement extends LStatement{
        public LColorOp op = LColorOp.mul;
        public String color = "color", p1 = "a", p2 = "b", p3 = "c", p4 = "d", p5 = "e", result = "result";
        
        public ColorOpStatement(){
        }
        
        public ColorOpStatement(String op, String color, String p1, String p2, String p3, String p4, String p5, String result){
            try{
                this.op = LColorOp.valueOf(op);
            }catch(Throwable ignored){}
            this.color = color;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.p4 = p4;
            this.p5 = p5;
            this.result = result;
        }
        
        @Override
        public void build(Table table){
            rebuild(table);
        }
        
        void rebuild(Table table){
            table.clearChildren();
            fields(table, result, str -> result = str);
            table.add(" = ");
            table.button(b -> {
                b.label(() -> op.name());
                b.clicked(() -> showSelect(b, LColorOp.all, op, o -> {
                    op = o;
                    rebuild(table);
                }, 2, c -> c.size(140f, 40f)));
            }, Styles.logict, () -> {}).size(140f, 40f).pad(4f).color(table.color);
            row(table);
            fields(table, "color", color, str -> color = str);
            switch(op){
                case lerpRGBA -> {
                    row(table);
                    fields(table, "r", p1, str -> p1 = str);
                    row(table);
                    fields(table, "g", p2, str -> p2 = str);
                    row(table);
                    fields(table, "b", p3, str -> p3 = str);
                    row(table);
                    fields(table, "a", p4, str -> p4 = str);
                    row(table);
                    fields(table, "t", p5, str -> p5 = str);
                }
                case mulRGBA, addRGBA, subRGBA -> {
                    row(table);
                    fields(table, "r", p1, str -> p1 = str);
                    row(table);
                    fields(table, "g", p2, str -> p2 = str);
                    row(table);
                    fields(table, "b", p3, str -> p3 = str);
                    row(table);
                    fields(table, "a", p4, str -> p4 = str);
                }
                case addRGB, subRGB -> {
                    row(table);
                    fields(table, "r", p1, str -> p1 = str);
                    row(table);
                    fields(table, "g", p2, str -> p2 = str);
                    row(table);
                    fields(table, "b", p3, str -> p3 = str);
                }
                case lerp -> {
                    row(table);
                    fields(table, "color2", p1, str -> p1 = str);
                    row(table);
                    fields(table, "t", p2, str -> p2 = str);
                }
                case mul, mulA, setHue, setSat, setVal, shiftHue, shiftSat, shiftVal -> {
                    row(table);
                    fields(table, "value", p1, str -> p1 = str);
                }
                case mulC, addC, subC -> {
                    row(table);
                    fields(table, "color2", p1, str -> p1 = str);
                }
            }
        }
        
        @Override
        public LInstruction build(LAssembler b){
            return new ColorOpI(op, b.var(color), b.var(p1), b.var(p2), b.var(p3), b.var(p4), b.var(p5), b.var(result));
        }
        
        @Override
        public LCategory category(){
            return LCategory.operation;
        }
        
        @Override
        public void write(StringBuilder builder){
            builder
                .append("colorop ")
                .append(op.name())
                .append(" ")
                .append(color)
                .append(" ")
                .append(p1)
                .append(" ")
                .append(p2)
                .append(" ")
                .append(p3)
                .append(" ")
                .append(p4)
                .append(" ")
                .append(p5)
                .append(" ")
                .append(result);
        }
    }
    
    // World
    
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
    
    public static class AddPuddleStatement extends LStatement{
        public String x = "10", y = "10", liquid = "@slag", amount = "5";
        
        public AddPuddleStatement(){
        }
        
        public AddPuddleStatement(String x, String y, String liquid, String amount){
            this.x = x;
            this.y = y;
            this.liquid = liquid;
            this.amount = amount;
        }
        
        @Override
        public void build(Table table){
            fields(table, "x", x, str -> x = str);
            fields(table, "y", y, str -> y = str);
            row(table);
            fields(table, "liquid", liquid, str -> liquid = str);
            row(table);
            fields(table, "amount", amount, str -> amount = str);
        }
        
        @Override
        public LInstruction build(LAssembler b){
            return new AddPuddleI(b.var(x), b.var(y), b.var(liquid), b.var(amount));
        }
        
        @Override
        public boolean privileged(){
            return true;
        }
        
        @Override
        public LCategory category(){
            return LCategory.world;
        }
        
        @Override
        public void write(StringBuilder builder){
            builder
                .append("addpuddle ")
                .append(x)
                .append(" ")
                .append(y)
                .append(" ")
                .append(liquid)
                .append(" ")
                .append(amount);
        }
    }
    
    public static class AddFireStatement extends LStatement{
        public String x = "10", y = "10";
        
        public AddFireStatement(){
        }
        
        public AddFireStatement(String x, String y){
            this.x = x;
            this.y = y;
        }
        
        @Override
        public void build(Table table){
            fields(table, "x", x, str -> x = str);
            fields(table, "y", y, str -> y = str);
        }
        
        @Override
        public LInstruction build(LAssembler b){
            return new AddFireI(b.var(x), b.var(y));
        }
        
        @Override
        public boolean privileged(){
            return true;
        }
        
        @Override
        public LCategory category(){
            return LCategory.world;
        }
        
        @Override
        public void write(StringBuilder builder){
            builder
                .append("addfire ")
                .append(x)
                .append(" ")
                .append(y);
        }
    }
    
    // Miscellaneous
    
    public static class ArrivalGifStatement extends LStatement{
        @Override
        public void build(Table table){
            table.image(ThisThing.arrival);
        }
        
        @Override
        public LInstruction build(LAssembler b){
            return null;
        }
        
        @Override
        public LCategory category(){
            return TTLogic.categoryMisc;
        }
        
        @Override
        public void write(StringBuilder builder){
            builder.append("arrivalgif");
        }
        
        @Override
        public String name(){
            return "arrival.gif";
        }
    }
    
    public static void load(){
        // IO
        
        registerStatement("readmessage", args -> new ReadMessageStatement(args[1], args[2]), ReadMessageStatement::new);
        
        // Operation
        
        registerStatement("rand", args -> new RandStatement(args[1], args[2], args[3], args[4]), RandStatement::new);
        registerStatement("stringop", args -> new StringOpStatement(args[1], args[2], args[3], args[4]), StringOpStatement::new);
        registerStatement("colorop", args -> new ColorOpStatement(args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8]), ColorOpStatement::new);
        
        // World
        
        registerStatement("shake", args -> new ShakeStatement(args[1], args[2], args[3]), ShakeStatement::new);
        registerStatement("addpuddle", args -> new AddPuddleStatement(args[1], args[2], args[3], args[4]), AddPuddleStatement::new);
        registerStatement("addfire", args -> new AddFireStatement(args[1], args[2]), AddFireStatement::new);
        
        // Miscellaneous
        
        registerStatement("arrivalgif", args -> new ArrivalGifStatement(), ArrivalGifStatement::new);
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
