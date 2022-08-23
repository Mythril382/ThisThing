package thing.logic;

import mindustry.logic.*;

public enum LRand{
    nextDouble(a -> GlobalVars.rand.nextDouble()),
    nextLong(a -> (double)GlobalVars.rand.nextLong()),
    nextInt(a -> (double)GlobalVars.rand.nextInt()),
    nextFloat(a -> (double)GlobalVars.rand.nextFloat()),
    random(a -> (double)GlobalVars.rand.random((float)a)),
    random2((a, b) -> (double)GlobalVars.rand.random((float)a, (float)b));
    
    public static final LRand[] all = values();
    
    public final RandFunc func1;
    public final RandFunc2 func2;
    
    LRand(RandFunc func){
        func1 = func;
        func2 = null;
    }
    
    LRand(RandFunc2 func){
        func2 = func;
        func1 = null;
    }
    
    interface RandFunc{
        double get(double a);
    }
    
    interface RandFunc2{
        double get(double a, double b);
    }
}