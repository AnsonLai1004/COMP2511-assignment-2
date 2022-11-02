package dungeonmania.entities.Logical;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public interface Logic {
    public boolean achieved(GameMap map, Entity entity);
}
