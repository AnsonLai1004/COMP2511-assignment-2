package dungeonmania;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.Goal;
import dungeonmania.goals.GoalFactory;
import dungeonmania.map.GameMap;
import dungeonmania.map.GraphNode;
import dungeonmania.map.GraphNodeFactory;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.ResponseBuilder;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

public class DungeonManiaController {
    private Game game = null;

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        if (!dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException(dungeonName + " is not a dungeon that exists");
        }

        if (!configs().contains(configName)) {
            throw new IllegalArgumentException(configName + " is not a configuration that exists");
        }

        try {
            GameBuilder builder = new GameBuilder();
            game = builder.setConfigName(configName).setDungeonName(dungeonName).buildGame();
            return ResponseBuilder.getDungeonResponse(game);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return null;
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        return ResponseBuilder.getDungeonResponse(game.tick(itemUsedId));
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        return ResponseBuilder.getDungeonResponse(game.tick(movementDirection));
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        List<String> validBuildables = List.of("bow", "shield", "midnight_armour", "sceptre");
        if (!validBuildables.contains(buildable)) {
            throw new IllegalArgumentException("Only bow, shield, midnight_armour and sceptre can be built");
        }

        return ResponseBuilder.getDungeonResponse(game.build(buildable));
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return ResponseBuilder.getDungeonResponse(game.interact(entityId));
    }

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        DungeonResponse dres = ResponseBuilder.getDungeonResponse(game);
        JSONObject jsonObject = new JSONObject();
        // config
        jsonObject.put("config", game.getEntityFactory().getConfig());
        // dungeonId
        jsonObject.put("dungeonId", dres.getDungeonId());
        // dungeonName
        jsonObject.put("dungeonName", dres.getDungeonName());
        // entities and their position
        JSONArray entitiesArr = new JSONArray();
        List<EntityResponse> entities = dres.getEntities();
        for (EntityResponse entity : entities) {
            JSONObject e = new JSONObject();
            e.put("type", entity.getType());
            e.put("x", entity.getPosition().getX());
            e.put("y", entity.getPosition().getY());
            entitiesArr.put(e);
        }
        jsonObject.put("entities", entitiesArr);
        // inventory
        JSONArray invArr = new JSONArray();
        List<ItemResponse> invs = dres.getInventory();
        for (ItemResponse inv : invs) {
            JSONObject i = new JSONObject();
            i.put("id", inv.getId());
            i.put("type", inv.getType());
            invArr.put(i);
        }
        jsonObject.put("inventory", invArr);
        // battles

        // buildables
        // JSONArray buildablesArr = new JSONArray();
        // List<String> builds = dres.getBuildables();
        // for (String b : builds) {

        // }
        // goals 
        jsonObject.put("goals", dres.getGoals());

        FileWriter file;
        try {
            file = new FileWriter("src\\main\\java\\dungeonmania\\Saved\\" + name + ".json");
            file.write(jsonObject.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dres;
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        JSONObject obj;
        String file = "src\\main\\java\\dungeonmania\\Saved\\" + name + ".json";
        try {
            Game game = new Game(obj.getString("dungeonName"));
            EntityFactory factory = new EntityFactory(obj.getJSONObject("config"));
            game.setEntityFactory(factory);
            // build map
            GameMap map = new GameMap();
            map.setGame(game);
            obj.getJSONArray("entities").forEach(e -> {
                JSONObject jsonEntity = (JSONObject) e;
                GraphNode newNode = GraphNodeFactory.createEntity(jsonEntity, game.getEntityFactory());
                Entity entity = newNode.getEntities().get(0);
    
                if (newNode != null)
                    map.addNode(newNode);
    
                if (entity instanceof Player)
                    map.setPlayer((Player) entity);
            });
            game.setMap(map);
            // build goal
            if (!obj.isNull("goal-condition")) {
                Goal goal = GoalFactory.createGoal(obj.getJSONObject("goal-condition"), obj.getJSONObject("config"));
                game.setGoals(goal);
            }
            game.init();        
        } catch (IOException e) {
            obj = null;
        }
        return ResponseBuilder.getDungeonResponse(game);
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        return new ArrayList<>();
    }

    /**
     * /game/new/generate
     */
    public DungeonResponse generateDungeon(
            int xStart, int yStart, int xEnd, int yEnd, String configName) throws IllegalArgumentException {
        return null;
    }

    /**
     * /game/rewind
     */
    public DungeonResponse rewind(int ticks) throws IllegalArgumentException {
        return null;
    }

}
