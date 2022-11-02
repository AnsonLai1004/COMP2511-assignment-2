package dungeonmania.entities.Logical;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public class CoAnd implements Logic {

    @Override
    public boolean achieved(GameMap map, Entity entity) {
        // TODO Auto-generated method stub
        return false;
    }
    // In summary (the spec update is going to be pushed very soon now) 
    // we want all adjacent active conductors to have been activated on the same tick, 
    // and there must be at least 2 of such active conductors
}
