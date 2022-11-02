package dungeonmania.entities.Logical;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends LogicalEntity {

    public SwitchDoor(Position position, String logic) {
        super(position, logic);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (super.isActivated() || entity instanceof Spider) {
            return true;
        }
        return false;
    }
}

