package thing.logic;

import arc.audio.*;
import arc.graphics.*;
import arc.input.*;
import arc.input.KeyBind.*;
import arc.util.*;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.logic.*;
import mindustry.logic.LExecutor.*;
import mindustry.type.*;
import mindustry.world.blocks.logic.MessageBlock.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class TTLInstructions{
    // IO
    
    public static class ReadMessageI implements LInstruction{
        public LVar building, result;
        
        public ReadMessageI(){
        }
        
        public ReadMessageI(LVar building, LVar result){
            this.building = building;
            this.result = result;
        }
        
        @Override
        public void run(LExecutor exec){
            if(building.obj() instanceof MessageBuild message) result.setobj(message.message.toString());
        }
    }
    
    // Operation
    
    public static class RandI implements LInstruction{
        public LRand method = LRand.nextDouble;
        public LVar p1, p2, result;
        
        public RandI(){
        }
        
        public RandI(LRand method, LVar p1, LVar p2, LVar result){
            this.method = method;
            this.p1 = p1;
            this.p2 = p2;
            this.result = result;
        }
        
        @Override
        public void run(LExecutor exec){
            if(method.func1 != null){
                result.setnum(method.func1.get(p1.num()));
            }else if(method.func2 != null){
                result.setnum(method.func2.get(p1.num(), p2.num()));
            }
        }
    }
    
    public static class StringOpI implements LInstruction{
        public LStringOp op = LStringOp.charAt;
        public LVar string, p1, result;
        
        public StringOpI(){
        }
        
        public StringOpI(LStringOp op, LVar string, LVar p1, LVar result){
            this.op = op;
            this.string = string;
            this.p1 = p1;
            this.result = result;
        }
        
        @Override
        public void run(LExecutor exec){
            if(string.obj() instanceof String str){
                switch(op){
                    case charAt -> {
                        try{
                            result.setobj(String.valueOf(str.charAt(p1.numi())));
                        }catch(Throwable ignored){
                            result.setobj(null);
                        }
                    }
                    case concat -> {
                        result.setobj(str.concat(p1.isobj ? PrintI.toString(p1.obj()) : p1.num() % 1 == 0 ? Integer.toString(p1.numi()) : Double.toString(p1.num())));
                    }
                    case isEmpty -> result.setbool(str.isEmpty());
                    case length -> result.setnum(str.length());
                    case toLowerCase -> result.setobj(str.toLowerCase(bundle.getLocale()));
                    case toUpperCase -> result.setobj(str.toUpperCase(bundle.getLocale()));
                }
            }
        }
    }
    
    public static class ColorOpI implements LInstruction{
        public LColorOp op = LColorOp.mul;
        public LVar color, p1, p2, p3, p4, p5, result;
        
        public ColorOpI(){
        }
        
        public ColorOpI(LColorOp op, LVar color, LVar p1, LVar p2, LVar p3, LVar p4, LVar p5, LVar result){
            this.op = op;
            this.color = color;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.p4 = p4;
            this.p5 = p5;
            this.result = result;
        }
        
        @Override
        public void run(LExecutor exec){
            Color col = unpack(color.num());
            
            switch(op){
                case mul -> result.setnum(pack(col.mul(p1.numf())));
                case mulC -> result.setnum(pack(col.mul(unpack(p1.num()))));
                case mulA -> result.setnum(pack(col.mula(p1.numf())));
                case mulRGBA -> result.setnum(pack(col.mul(p1.numf(), p2.numf(), p3.numf(), p4.numf())));
                case addRGBA -> result.setnum(pack(col.add(p1.numf(), p2.numf(), p3.numf(), p4.numf())));
                case addRGB -> result.setnum(pack(col.add(p1.numf(), p2.numf(), p3.numf())));
                case addC -> result.setnum(pack(col.add(unpack(p1.num()))));
                case subRGBA -> result.setnum(pack(col.sub(p1.numf(), p2.numf(), p3.numf(), p4.numf())));
                case subRGB -> result.setnum(pack(col.sub(p1.numf(), p2.numf(), p3.numf())));
                case subC -> result.setnum(pack(col.sub(unpack(p1.num()))));
                case sum -> result.setnum((double)col.sum());
                case inv -> result.setnum(pack(col.inv()));
                case lerp -> result.setnum(pack(col.lerp(unpack(p1.num()), p2.numf())));
                case lerpRGBA -> result.setnum(pack(col.lerp(p1.numf(), p2.numf(), p3.numf(), p4.numf(), p5.numf())));
                case premulA -> result.setnum(pack(col.premultiplyAlpha()));
                case hue -> result.setnum((double)col.hue());
                case sat -> result.setnum((double)col.saturation());
                case val -> result.setnum((double)col.value());
                case setHue -> result.setnum(pack(col.hue(p1.numf())));
                case setSat -> result.setnum(pack(col.saturation(p1.numf())));
                case setVal -> result.setnum(pack(col.value(p1.numf())));
                case shiftHue -> result.setnum(pack(col.shiftHue(p1.numf())));
                case shiftSat -> result.setnum(pack(col.shiftSaturation(p1.numf())));
                case shiftVal -> result.setnum(pack(col.shiftValue(p1.numf())));
                case colToString -> result.setobj(col.toString());
            }
        }
        
        public static Color unpack(double col){
            int
            val = (int)(Double.doubleToRawLongBits(col)),
            r = ((val & 0xff000000) >>> 24),
            g = ((val & 0x00ff0000) >>> 16),
            b = ((val & 0x0000ff00) >>> 8),
            a = ((val & 0x000000ff));
            
            return new Color(r, g, b, a);
        }
        
        public static double pack(Color col){
            return Color.toDoubleBits(col.r, col.g, col.b, col.a);
        }
    }
    
    // Unit
    
    public static class KeybindSensorI implements LInstruction{
        public String bind;
        public LVar result;
        
        public KeybindSensorI(){
        }
        
        public KeybindSensorI(String bind, LVar result){
            this.bind = bind;
            this.result = result;
        }
        
        @Override
        public void run(LExecutor exec){
            @Nullable KeyBind found = KeyBind.all.find(b -> b.name.equals(bind));
            if(found != null){
                if(found.defaultValue instanceof Axis){
                    result.setnum(input.axis(found));
                }else{
                    result.setbool(input.keyDown(found));
                }
            }
        }
    }
    
    // World
    
    public static class ShakeI implements LInstruction{
        public LVar intensity, x, y;
        
        public ShakeI(){
        }
        
        public ShakeI(LVar intensity, LVar x, LVar y){
            this.intensity = intensity;
            this.x = x;
            this.y = y;
        }
        
        @Override
        public void run(LExecutor exec){
            Effect.shake(intensity.numf(), intensity.numf(), World.unconv(x.numf()), World.unconv(y.numf()));
        }
    }
    
    public static class AddPuddleI implements LInstruction{
        public LVar x, y, liquid, amount;
        
        public AddPuddleI(){
        }
        
        public AddPuddleI(LVar x, LVar y, LVar liquid, LVar amount){
            this.x = x;
            this.y = y;
            this.liquid = liquid;
            this.amount = amount;
        }
        
        @Override
        public void run(LExecutor exec){
            if(liquid.obj() instanceof Liquid l) Puddles.deposit(world.tile(x.numi(), y.numi()), l, amount.numf());
        }
    }
    
    public static class AddFireI implements LInstruction{
        public LVar x, y;
        
        public AddFireI(){
        }
        
        public AddFireI(LVar x, LVar y){
            this.x = x;
            this.y = y;
        }
        
        @Override
        public void run(LExecutor exec){
            Fires.create(world.tile(x.numi(), y.numi()));
        }
    }
}
