package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SunStoneTest {
    @Test
    @DisplayName("Test player can use a sunstone to open and walk through a door")
    public void useSunStoneWalkThroughOpenDoor() {
        // P1  Sunstone  Door
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_useSunStoneWalkThroughOpenDoor",
            "c_sunStoneTest_useSunStoneWalkThroughOpenDoor");

        // pick up sunstone
        res = dmc.tick(Direction.RIGHT);
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // walk through door and check sunstone is retained
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @DisplayName("Test sunstone is retained after being used")
    public void sunStoneIsRetained()  {
        //TODO:

    }

    @Test
    @DisplayName("Test sunstone cannot be used to bribe")
    public void sunStoneCannotBribe()  {
        //                                                          Wall     Wall     Wall    Wall    Wall
        // P1       P2/Sunstone      P3/Treasure    P4/Treasure      M4       M3       M2     M1      Wall
        //                                                          Wall     Wall     Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_cannotBribe",
            "c_sunStoneTest_cannotBribe");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // pick up first treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(new Position(7, 1), getMercPos(res));

        // attempt bribe
        assertThrows(InvalidActionException.class, () ->
                dmc.interact(mercId)
        );
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // pick up second treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(6, 1), getMercPos(res));

        // attempt bribe
        assertThrows(InvalidActionException.class, () ->
                dmc.interact(mercId)
        );
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());

        // pick up third treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(3, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(5, 1), getMercPos(res));

        // cannot bribe
        assertThrows(InvalidActionException.class, () ->
                dmc.interact(mercId)
        );
        assertEquals(3, TestUtils.getInventory(res, "treasure").size());
    }

    @Test
    @DisplayName("Test sunstone counts towards treasure goal")
    public void treasureGoal() {
        // P1      Sunstone  Treasure
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_treasureGoal",
            "c_sunStoneTest_treasureGoal");

        // move player to right
        res = dmc.tick(Direction.RIGHT);

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @DisplayName("Test sunstone can replace another material in building entities")
    public void sunStoneBuildEntities()  {
        // TODO:
    }
    // can add switch door test later when swith door is implemented...

    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }
}
