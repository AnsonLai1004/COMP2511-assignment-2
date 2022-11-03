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
    @Test
    @DisplayName("Test wire")
    public void wireActivateLightBulb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_LogicSwitchesTest_wire",
            "c_LogicSwitchesTest_lightBulbBasic");

        assertTrue(boulderAt(res, 2, 1));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(boulderAt(res, 3, 1));
        assertTrue(lightbulbIsActivated(res, 6, 1));
    }
    @Test
    @DisplayName("Success case for And + test logical entitiy is not conductor")
    public void andLogicSuccessCase() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_LogicSwitchesTest_andSuccess",
            "c_LogicSwitchesTest_lightBulbBasic");

        assertTrue(boulderAt(res, 2, 1));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(boulderAt(res, 3, 1));
        assertTrue(lightbulbIsActivated(res, 4, 1));

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3, 2), TestUtils.getEntities(res, "player").get(0).getPosition());
    }
    @Test
    @DisplayName("Fail case for And")
    public void andLogicFailCase() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_LogicSwitchesTest_andFail",
            "c_LogicSwitchesTest_lightBulbBasic");

        assertTrue(boulderAt(res, 2, 1));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(boulderAt(res, 3, 1));
        assertTrue(!lightbulbIsActivated(res, 6, 1));
    }

    @Test
    @DisplayName("Success case for Or")
    public void orLogicSuccessCase() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_LogicSwitchesTest_orSuccess",
            "c_LogicSwitchesTest_lightBulbBasic");

        assertTrue(boulderAt(res, 2, 1));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(boulderAt(res, 3, 1));
        assertTrue(lightbulbIsActivated(res, 6, 1));
    }
    @Test
    @DisplayName("Success case for Xor")
    public void xorLogicSuccessCase() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_LogicSwitchesTest_XorSuccess",
            "c_LogicSwitchesTest_lightBulbBasic");

        assertTrue(boulderAt(res, 2, 1));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(boulderAt(res, 3, 1));
        assertTrue(lightbulbIsActivated(res, 6, 1));
    }
    @Test
    @DisplayName("Test Xor fail at first then pass")
    public void xorLogicFailCase() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_LogicSwitchesTest_XorFail",
            "c_LogicSwitchesTest_lightBulbBasic");

        assertTrue(boulderAt(res, 2, 1));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(boulderAt(res, 3, 1));
        assertTrue(!lightbulbIsActivated(res, 6, 1));

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        assertTrue(boulderAt(res, 3, 0));
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 2), TestUtils.getEntities(res, "player").get(0).getPosition());
        assertTrue(lightbulbIsActivated(res, 6, 1));
    }
    @Test
    @DisplayName("Success case for Co_And")
    public void coAndLogicSuccessCase() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_LogicSwitchesTest_CoAndSuccess",
            "c_LogicSwitchesTest_lightBulbBasic");

        assertTrue(boulderAt(res, 2, 1));
        res = dmc.tick(Direction.RIGHT);
        assertTrue(boulderAt(res, 3, 1));
        assertTrue(lightbulbIsActivated(res, 6, 1));
    }
    @Test
    @DisplayName("Fail case for Co_And")
    public void coAndLogicFailCase() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_LogicSwitchesTest_CoAndFail",
            "c_LogicSwitchesTest_lightBulbBasic");

        assertTrue(boulderAt(res, 2, 1));
        assertTrue(boulderAt(res, 5, 2));
        res = dmc.tick(Direction.RIGHT);
        assertTrue(boulderAt(res, 3, 1));
        assertEquals(new Position(2, 1), TestUtils.getEntities(res, "player").get(0).getPosition());

        assertTrue(!lightbulbIsActivated(res, 6, 1));

        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(2, 2), TestUtils.getEntities(res, "player").get(0).getPosition());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3, 2), TestUtils.getEntities(res, "player").get(0).getPosition());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(4, 2), TestUtils.getEntities(res, "player").get(0).getPosition());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 2), TestUtils.getEntities(res, "player").get(0).getPosition());
        assertTrue(boulderAt(res, 6, 2));

        assertTrue(!lightbulbIsActivated(res, 6, 1));
    }
    @Test
    @DisplayName("Wire can move onto")
    public void wireCanMoveOnto() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_LogicSwitchesTest_wireMoveOnto",
            "c_LogicSwitchesTest_lightBulbBasic");

        assertEquals(new Position(2, 1), TestUtils.getEntities(res, "player").get(0).getPosition());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3, 1), TestUtils.getEntities(res, "player").get(0).getPosition());

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
