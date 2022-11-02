package dungeonmania.entities.Logical.rules;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Switch;
import dungeonmania.entities.Wire;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class CoAnd implements Logic {
    private int numActivated = 0;
    private int numConductor = 0;
    private int prevActivated = 0;

    @Override
    public boolean achieved(GameMap map, Entity entity) {
        boolean res;
        List<Position> pos = entity.getPosition().getCardinallyAdjacentPositions();
        pos.stream().forEach(node -> {
            map.getEntities(node).forEach(e -> {
                if (e instanceof Switch) {
                    if (((Switch) e).isActivated()) numActivated++;
                    numConductor++;
                }

                if (e instanceof Wire) {
                    if (((Wire) e).isActivated()) numActivated++;
                    numConductor++;
                }

            });
        });
        if (numActivated >= 2 && numConductor == numActivated - prevActivated) {
            res = true;
        } else {
            res = false;
        }
        prevActivated = numActivated;
        return res;
    }
}
