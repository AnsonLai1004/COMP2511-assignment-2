package dungeonmania.entities;
import dungeonmania.entities.collectables.Treasure;


public class Bribe {
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
}
