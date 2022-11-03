package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenceTest {
    @Test//"c_persistenceTest_position"
    @DisplayName("Preserve the position of entities on the map")
    public void positionPersistence() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_persistenceTest_position", "c_persistenceTest_position");
        
        assertEquals(new Position(4, 0), getMercPos(res));
        assertEquals(new Position(0, 0), TestUtils.getPlayerPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3, 0), getMercPos(res));
        assertEquals(new Position(1, 0), TestUtils.getPlayerPos(res));
        assertEquals(
            new Position(1, 1), 
            TestUtils.getEntities(res, "treasure").get(0).getPosition());
        assertEquals(
            new Position(4, 4), 
            TestUtils.getEntities(res, "exit").get(0).getPosition());
        // save game
        res = assertDoesNotThrow(() -> dmc.saveGame("game1"));
        /* 
        res = dmc.tick(Direction.RIGHT);
        // load game
        res = assertDoesNotThrow(() -> dmc.loadGame("game1"));
        assertEquals(new Position(3, 0), getMercPos(res));
        assertEquals(new Position(1, 0), TestUtils.getPlayerPos(res));
        assertEquals(
            new Position(1, 1), 
            TestUtils.getEntities(res, "treasure").get(0).getPosition());
        assertEquals(
            new Position(4, 4), 
            TestUtils.getEntities(res, "exit").get(0).getPosition());
        */
    }

    @Test
    @DisplayName("Test persistence of items")
    public void itemsPersistence() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_AllyTest_followPlayer", "c_AllyTest_followPlayer");
        
    }

    @Test
    @DisplayName("Test persistence of bribing")
    public void bribingPersistence() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_AllyTest_followPlayer", "c_AllyTest_followPlayer");
       
    }

    @Test
    @DisplayName("Test persistence of mind-control")
    public void mindControlPersistence() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_AllyTest_followPlayer", "c_AllyTest_followPlayer");
       
    }
    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }
}
