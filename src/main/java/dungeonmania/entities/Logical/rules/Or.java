package dungeonmania.entities.Logical.rules;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Switch;
import dungeonmania.entities.Wire;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Or implements Logic {
    @Override
    public boolean achieved(GameMap map, Entity entity) {
        List<Position> pos = entity.getPosition().getCardinallyAdjacentPositions();
        for (Position p : pos) {
            List<Entity> entities = map.getEntities(p);
            for (Entity e : entities) {
                if (e instanceof Switch) {
                    if (((Switch) e).isActivated()) return true;
                }
                if (e instanceof Wire) {
                    if (((Wire) e).isActivated()) return true;
                }
            }
        }
        return false;
    }
}

