package dungeonmania.entities;
import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;


public class Bribe {
    private boolean isCardinallyAdjacent = false;

      /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    public boolean canBeBribed(Player player, Bribeable entity) {
        return entity.getBribeRadius() >= 0 && player.countEntityOfType(Treasure.class) >= entity.getBribeAmount();
    }

    /**
     * bribe the merc
     */
    public void bribe(Player player, Bribeable entity) {
        for (int i = 0; i < entity.getBribeAmount(); i++) {
            player.use(Treasure.class);
        }

    }

    public void allyMovement(Game game, Entity entity) {
        Position nextPos;
        GameMap map = game.getMap();
        if (((Bribeable) entity).isAllied() && isCardinallyAdjacent) {
            // follow player
            nextPos = map.getPlayer().getPreviousPosition();
            map.moveTo(entity, nextPos);
        } else {
            if (checkIsCardinallyAdjacent(map, entity)) return;
            // Follow hostile
            nextPos = map.dijkstraPathFind(entity.getPosition(), map.getPlayer().getPosition(), entity);
            map.moveTo(entity, nextPos);
        }
    }

    // when ally is cardinally adjacent to player,
    // set isCardinallyAdjacent to true and teleport ally to player previous position
    public boolean checkIsCardinallyAdjacent(GameMap map, Entity entity) {
        if (!((Bribeable) entity).isAllied()) return false;
        List<Position> pos = entity.getPosition().getCardinallyAdjacentPositions();
        Position playerPos = map.getPlayer().getPosition();
        if (pos.contains(playerPos)) {
            this.isCardinallyAdjacent = true;
            Position playerPrePos = map.getPlayer().getPreviousPosition();
            map.moveTo(entity, playerPrePos);
            return true;
        }
        return false;
    }
}
