package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AllyMovementTest {
    @Test
    @DisplayName("Test ally follow player")
    public void AllyMovement() {
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        // P1       P2      P3/P7    M5     M4      M3      M2      M1      .      Wall
        //               P4/P6/M8    M6     Wall    Wall   Wall    Wall    Wall    Wall
        //                  P5/M7          

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_AllyTest_followPlayer", "c_AllyTest_followPlayer");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(new Position(7, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(6, 1), getMercPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), getMercPos(res));
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(4, 1), getMercPos(res));
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(4, 2), getMercPos(res));
        res = dmc.tick(Direction.UP);
        // should teleport to player previous position
        assertEquals(new Position(3, 3), getMercPos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(3, 2), getMercPos(res));
    }
    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }
}
