package dungeonmania.entities;

import java.util.HashMap;
import java.util.Map;

import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwampTile extends Entity {
    private int movementFactor;
    private Map<Entity, Integer> stuckEntities = new HashMap<>();

    public SwampTile(Position position, int movementFactor) {
        super(position.asLayer(Entity.FLOOR_LAYER));
        this.movementFactor = movementFactor;
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        if (stuckEntities.containsKey(entity)) {
            stuckEntities.remove(entity);
            ((Enemy) entity).setCurSwamp(null);
        }
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Enemy && !stuckEntities.containsKey(entity)) {
            stuckEntities.put(entity, movementFactor);
            ((Enemy) entity).setCurSwamp(this);
        }
    }

    public void setMovementFactor(int movementFactor) {
        this.movementFactor = movementFactor;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public boolean canMoveAway(GameMap map, Entity entity) {
        if (!stuckEntities.containsKey(entity) || stuckEntities.get(entity) == 0) {
            return true;
        }
        stuckEntities.put(entity, stuckEntities.get(entity) - 1);
        return false;
    }
}
