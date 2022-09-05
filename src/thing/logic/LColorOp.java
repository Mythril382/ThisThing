package thing.logic;

public enum LColorOp{
    mul,
    mulC,
    mulA,
    mulRGBA,
    addRGBA,
    addRGB,
    addC,
    subRGBA,
    subRGB,
    subC,
    sum,
    clamp,
    inv,
    lerp,
    lerpRGBA,
    preMulA,
    hue,
    sat,
    val,
    setHue,
    setSat,
    setVal,
    shiftHue,
    shiftSat,
    shiftVal,
    toString;
    
    public static final LColorOp[] all = values();
}
