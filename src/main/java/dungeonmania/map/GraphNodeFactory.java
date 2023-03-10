package dungeonmania.map;

import org.json.JSONObject;

import dungeonmania.entities.EntityFactory;

public class GraphNodeFactory {
    public static GraphNode createEntity(JSONObject jsonEntity, EntityFactory factory) {
        return constructEntity(jsonEntity, factory);
    }

    private static GraphNode constructEntity(JSONObject jsonEntity, EntityFactory factory) {
        switch (jsonEntity.getString("type")) {
        case "player":
        case "zombie_toast":
        case "zombie_toast_spawner":
        case "mercenary":
        case "assassin":
        case "wall":
        case "swamp_tile":
        case "boulder":
        case "switch":
        case "exit":
        case "treasure":
        case "wood":
        case "arrow":
        case "bomb":
        case "invisibility_potion":
        case "invincibility_potion":
        case "portal":
        case "sword":
        case "spider":
        case "door":
        case "sun_stone":
        case "switch_door":
        case "wire":
        case "light_bulb_off":
        case "midnight_armour":
        case "key":
            return new GraphNode(factory.createEntity(jsonEntity));
        default:
            return null;
        }
    }
}
