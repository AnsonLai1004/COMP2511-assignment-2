package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicSwitchesTest {
    @Test
    @DisplayName("Test lightbulb can activate")
    public void lightBulbActivate() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_LogicSwitchesTest_lightBulbBasic",
            "c_LogicSwitchesTest_lightBulbBasic");

        assertTrue(boulderAt(res, 2, 1));

        res = dmc.tick(Direction.RIGHT);        
        assertTrue(boulderAt(res, 3, 1));
        assertTrue(lightbulbIsActivated(res, 4, 1));
    }
    
    @Test
    @DisplayName("Test switchDoor can activate")
    public void switchDoorActivate() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_LogicSwitchesTest_switchDoorBasic",
            "c_LogicSwitchesTest_lightBulbBasic");

        assertTrue(boulderAt(res, 2, 1));

        res = dmc.tick(Direction.RIGHT);        
        assertTrue(boulderAt(res, 3, 1));
        assertEquals(new Position(2, 1), TestUtils.getEntities(res, "player").get(0).getPosition());

        res = dmc.tick(Direction.DOWN);        
        assertEquals(new Position(2, 2), TestUtils.getEntities(res, "player").get(0).getPosition());
        
        res = dmc.tick(Direction.RIGHT);        
        assertEquals(new Position(3, 2), TestUtils.getEntities(res, "player").get(0).getPosition());
        
        res = dmc.tick(Direction.RIGHT);        
        assertEquals(new Position(4, 2), TestUtils.getEntities(res, "player").get(0).getPosition());

        res = dmc.tick(Direction.UP);
        // 4, 1 where the switch door is 
        assertEquals(new Position(4, 1), TestUtils.getEntities(res, "player").get(0).getPosition());
    }
    
    
    private boolean boulderAt(DungeonResponse res, int x, int y) {
        Position pos = new Position(x, y);
        return TestUtils.getEntitiesStream(res, "boulder").anyMatch(
            it -> it.getPosition().equals(pos)
        );
    }
    private boolean lightbulbIsActivated(DungeonResponse res, int x, int y) {
        Position pos = new Position(x, y);
        return TestUtils.getEntitiesStream(res, "light_bulb_on").anyMatch(
            it -> it.getPosition().equals(pos)
        );
    }

}
