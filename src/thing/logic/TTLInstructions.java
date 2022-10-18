package thing.logic;

import arc.audio.*;
import arc.graphics.*;
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
        public int building, result;
        
        public ReadMessageI(){
        }
        
        public ReadMessageI(int building, int result){
            this.building = building;
            this.result = result;
        }
        
        @Override
        public void run(LExecutor exec){
            if(exec.obj(building) instanceof MessageBuild message) exec.setobj(result, message.message.toString());
        }
    }
    
    // Operation
    
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
    
    public static class StringOpI implements LInstruction{
        public LStringOp op = LStringOp.charAt;
        public int string, p1, result;
        
        public StringOpI(){
        }
        
        public StringOpI(LStringOp op, int string, int p1, int result){
            this.op = op;
            this.string = string;
            this.p1 = p1;
            this.result = result;
        }
        
        @Override
        public void run(LExecutor exec){
            if(exec.obj(string) instanceof String str){
                switch(op){
                    case charAt -> {
                        try{
                            exec.setobj(result, String.valueOf(str.charAt(exec.numi(p1))));
                        }catch(Throwable ignored){
                            exec.setobj(result, null);
                        }
                    }
                    case concat -> {
                        Var v = exec.var(p1);
                        exec.setobj(result, str.concat(v.isobj ? PrintI.toString(v.objval) : v.numval % 1 == 0 ? Integer.toString((int)v.numval) : Double.toString(v.numval)));
                    }
                    case isEmpty -> exec.setbool(result, str.isEmpty());
                    case length -> exec.setnum(result, str.length());
                    case toLowerCase -> exec.setobj(result, str.toLowerCase(bundle.getLocale()));
                    case toUpperCase -> exec.setobj(result, str.toUpperCase(bundle.getLocale()));
                }
            }else{
                exec.setobj(result, null);
            }
        }
    }
    
    public static class ColorOpI implements LInstruction{
        public LColorOp op = LColorOp.mul;
        public int color, p1, p2, p3, p4, p5, result;
        
        public ColorOpI(){
        }
        
        public ColorOpI(LColorOp op, int color, int p1, int p2, int p3, int p4, int p5, int result){
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
            Color col = unpack(exec.num(color));
            
            switch(op){
                case mul -> exec.setnum(result, pack(col.mul(exec.numf(p1))));
                case mulC -> exec.setnum(result, pack(col.mul(unpack(exec.num(p1)))));
                case mulA -> exec.setnum(result, pack(col.mula(exec.numf(p1))));
                case mulRGBA -> exec.setnum(result, pack(col.mul(exec.numf(p1), exec.numf(p2), exec.numf(p3), exec.numf(p4))));
                case addRGBA -> exec.setnum(result, pack(col.add(exec.numf(p1), exec.numf(p2), exec.numf(p3), exec.numf(p4))));
                case addRGB -> exec.setnum(result, pack(col.add(exec.numf(p1), exec.numf(p2), exec.numf(p3))));
                case addC -> exec.setnum(result, pack(col.add(unpack(exec.num(p1)))));
                case subRGBA -> exec.setnum(result, pack(col.sub(exec.numf(p1), exec.numf(p2), exec.numf(p3), exec.numf(p4))));
                case subRGB -> exec.setnum(result, pack(col.sub(exec.numf(p1), exec.numf(p2), exec.numf(p3))));
                case subC -> exec.setnum(result, pack(col.sub(unpack(exec.num(p1)))));
                case sum -> exec.setnum(result, (double)col.sum());
                case inv -> exec.setnum(result, pack(col.inv()));
                case lerp -> exec.setnum(result, pack(col.lerp(unpack(exec.num(p1)), exec.numf(p2))));
                case lerpRGBA -> exec.setnum(result, pack(col.lerp(exec.numf(p1), exec.numf(p2), exec.numf(p3), exec.numf(p4), exec.numf(p5))));
                case premulA -> exec.setnum(result, pack(col.premultiplyAlpha()));
                case hue -> exec.setnum(result, (double)col.hue());
                case sat -> exec.setnum(result, (double)col.saturation());
                case val -> exec.setnum(result, (double)col.value());
                case setHue -> exec.setnum(result, pack(col.hue(exec.numf(p1))));
                case setSat -> exec.setnum(result, pack(col.saturation(exec.numf(p1))));
                case setVal -> exec.setnum(result, pack(col.value(exec.numf(p1))));
                case shiftHue -> exec.setnum(result, pack(col.shiftHue(exec.numf(p1))));
                case shiftSat -> exec.setnum(result, pack(col.shiftSaturation(exec.numf(p1))));
                case shiftVal -> exec.setnum(result, pack(col.shiftValue(exec.numf(p1))));
                case colToString -> exec.setobj(result, col.toString());
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
    
    // World
    
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
            if(((Object)Sounds.getSound(exec.numi(sound))) instanceof Sound sfx){
                if(exec.bool(usePos)){
                    sfx.at(World.unconv(exec.numf(x)), World.unconv(exec.numf(y)), exec.numf(pitch), exec.numf(volume));
                }else{
                    sfx.play(exec.numf(volume), exec.numf(pitch), 0f);
                }
            }
        }
    }
    
    public static class AddPuddleI implements LInstruction{
        public int x, y, liquid, amount;
        
        public AddPuddleI(){
        }
        
        public AddPuddleI(int x, int y, int liquid, int amount){
            this.x = x;
            this.y = y;
            this.liquid = liquid;
            this.amount = amount;
        }
        
        @Override
        public void run(LExecutor exec){
            if(exec.obj(liquid) instanceof Liquid l) Puddles.deposit(world.tile(exec.numi(x), exec.numi(y)), l, exec.numf(amount));
        }
    }
    
    public static class AddFireI implements LInstruction{
        public int x, y;
        
        public AddFireI(){
        }
        
        public AddFireI(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        @Override
        public void run(LExecutor exec){
            Fires.create(world.tile(exec.numi(x), exec.numi(y)));
        }
    }
}
