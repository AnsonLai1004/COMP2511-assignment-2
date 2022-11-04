package dungeonmania.goals;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Exit;
import dungeonmania.entities.Player;
import dungeonmania.entities.Switch;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.enemies.ZombieToastSpawner;
import dungeonmania.util.Position;

public class Goal {
    private String type;
    private int target;
    private Goal goal1;
    private Goal goal2;

    public Goal(String type) {
        this.type = type;
    }

    public Goal(String type, int target) {
        this.type = type;
        this.target = target;
    }

    public Goal(String type, Goal goal1, Goal goal2) {
        this.type = type;
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    /**
     * @return true if the goal has been achieved, false otherwise
     */
    public boolean achieved(Game game) {
        if (game.getPlayer() == null) return false;
        switch (type) {
            case "exit":
                Player character = game.getPlayer();
                Position pos = character.getPosition();
                List<Exit> es = game.getEntities(Exit.class);
                if (es == null || es.size() == 0) return false;
                return es
                    .stream()
                    .map(Entity::getPosition)
                    .anyMatch(pos::equals);
            case "boulders":
                return game.getEntities(Switch.class).stream().allMatch(s -> s.isActivated());
            case "treasure":
                return game.getInitialTreasureCount() - game.getEntities(Treasure.class).size()
                        - game.getEntities(SunStone.class).size() >= target;
            case "enemy":
                return game.getEnemiesDefeated() >= target && game.getEntities(ZombieToastSpawner.class).size() == 0;
            case "AND":
                return goal1.achieved(game) && goal2.achieved(game);
            case "OR":
                return goal1.achieved(game) || goal2.achieved(game);
            default:
                break;
        }
        return false;
    }

    public String toString(Game game) {
        if (this.achieved(game)) return "";
        switch (type) {
            case "exit":
                return ":exit";
            case "boulders":
                return ":boulders";
            case "treasure":
                return ":treasure";
            case "enemy":
                return ":enemy";
            case "AND":
                return "(" + goal1.toString(game) + " AND " + goal2.toString(game) + ")";
            case "OR":
                if (achieved(game)) return "";
                else return "(" + goal1.toString(game) + " OR " + goal2.toString(game) + ")";
            default:
                break;
        }
        return "";
    }

}
