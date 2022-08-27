package thing.ai;

import mindustry.ai.*;
import thing.ai.types.*;

public class UCommands{
    public static final UnitCommand
    patrolCommand = new UnitCommand("patrol", "effect", u -> new PatrolAI());
}