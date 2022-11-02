package dungeonmania.entities.Logical;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Logical.rules.*;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class LogicalEntity extends Entity {
    private Logic rule = null;
    private boolean activated = false;
    public LogicalEntity(Position position, String logic) {
        super(position);
        if (logic.equals("and")) {
            rule = new And();
        } else if (logic.equals("or")) {
            rule = new Or();
        } else if (logic.equals("xor")) {
            rule = new Xor();
        } else {
            rule = new CoAnd();
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public void activate(GameMap map) {
        if (rule.achieved(map, this)) {
            activated = true;
        } else {
            activated = false;
        }
    }

    public boolean isActivated() {
        return activated;
    }

    
}
