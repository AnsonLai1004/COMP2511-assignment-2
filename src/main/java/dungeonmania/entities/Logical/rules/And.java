package dungeonmania.entities.Logical.rules;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Switch;
import dungeonmania.entities.Wire;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class And implements Logic {
    private int numConductor = 0;
    private int numActivated = 0;

    @Override
    public boolean achieved(GameMap map, Entity entity) {
        boolean res = false;
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
        if (numActivated >= 2 && numConductor == numActivated) {
            res = true;
        }
        numActivated = 0;
        numConductor = 0;
        return res;
    }

}
