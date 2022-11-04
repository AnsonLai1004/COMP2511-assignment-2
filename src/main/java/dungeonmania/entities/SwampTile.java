package dungeonmania.entities;

import java.util.HashMap;
import java.util.Map;

import dungeonmania.entities.enemies.Assassin;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwampTile extends Entity {
    private int movementFactor;
    private Map<Entity, Integer> stuckEntities = new HashMap<>();

    public SwampTile(Position position, int movementFactor) {
        super(position);
        this.movementFactor = movementFactor;
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        if (stuckEntities.containsKey(entity)) {
            stuckEntities.remove(entity);
        }
    }
    
    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Enemy && !stuckEntities.containsKey(entity)) {
            if (!(entity instanceof Player) || !((Mercenary) entity).isAllied() || !((Assassin) entity).isAllied()) {
                stuckEntities.put(entity, movementFactor);
            }
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public boolean canMoveAway(GameMap map, Entity entity) {
        // not stuck OR unstuck this tick
        if (!stuckEntities.containsKey(entity) || stuckEntities.get(entity) == 0) {
            return true;
        }
        // stuck
        stuckEntities.put(entity, stuckEntities.get(entity) - 1);
        return false;
    }
}
