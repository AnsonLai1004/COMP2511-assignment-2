package dungeonmania.entities;

import java.util.List;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Wire extends Entity {
    private boolean activated;
    public Wire(Position position) {
        super(position);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(GameMap map, boolean activated) {
        this.activated = activated;
        if (activated) {
            List<Position> positions = getPosition().getCardinallyAdjacentPositions();
            for (Position pos : positions) {
                map.getEntities(pos).forEach(e -> {
                    if (e instanceof Wire) {
                        ((Wire) e).setActivated(map, true);
                    }
                });
            }
        }
    }
    

}
