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
    public void lightBulbActivateWithWire() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_LogicSwitchesTest_lightBulbBasic",
            "c_LogicSwitchesTest_lightBulbBasic");

        //assertTrue(boulderAt(res, 2, 1));

        res = dmc.tick(Direction.RIGHT);
        
        assertTrue(boulderAt(res, 3, 1));
        //asserTrue(lightbulbIsActivated(res, , 0)
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();

        // walk through door and check key is gone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }
    
    private boolean boulderAt(DungeonResponse res, int x, int y) {
        Position pos = new Position(x, y);
        return TestUtils.getEntitiesStream(res, "boulder").anyMatch(
            it -> it.getPosition().equals(pos)
        );
    }

}
