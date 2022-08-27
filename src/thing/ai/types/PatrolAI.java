package thing.ai.types;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.entities.units.*;

import static mindustry.Vars.*;

public class PatrolAI extends AIController{
    protected final Vec2 vecOut = new Vec2(), chaseVecOut = new Vec2();
    protected int pathId = -1, chasePathId = -1;
    
    public @Nullable Vec2 walkPoint;
    
    @Override
    public void updateMovement(){
        if(target == null){
            patrol();
        }else{
            walkPoint = null;
            chase();
        }
    }
    
    public void patrol(){
        if(walkPoint == null){
            findWalkPoint();
            return;
        }
        
        boolean move = controlPath.getPathPosition(unit, pathId, walkPoint, vecOut);
        if(move) moveTo(vecOut, (unit.type.hitSize / 2f));
        
        if(walkPoint.dst(unit.x, unit.y) <= unit.type.hitSize) walkPoint = null;
    }
    
    public void chase(){
        chasePathId = controlPath.nextTargetId();
        boolean move = controlPath.getPathPosition(unit, chasePathId, new Vec2(target.getX(), target.getY()), chaseVecOut);
        if(move) moveTo(chaseVecOut, unit.range() - unit.type.hitSize);
    }
    
    public void findWalkPoint(){
        pathId = controlPath.nextTargetId();
        
        float
        randX = Mathf.random(-unit.range(), unit.range()),
        randY = Mathf.random(-unit.range(), unit.range());
        
        boolean valid = controlPath.getPathPosition(unit, pathId, new Vec2(unit.x + randX, unit.y + randY), vecOut);
        
        if(valid) walkPoint = new Vec2(unit.x + randX, unit.y + randY);
        else walkPoint = null;
    }
}
