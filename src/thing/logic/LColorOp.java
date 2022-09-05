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
    inv,
    lerp,
    lerpRGBA,
    premulA,
    hue,
    sat,
    val,
    setHue,
    setSat,
    setVal,
    shiftHue,
    shiftSat,
    shiftVal,
    colToString;
    
    public static final LColorOp[] all = values();
}