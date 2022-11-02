package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Bribe;
import dungeonmania.entities.Bribeable;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable, Bribeable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;
    private int mcduration = 0;
    private boolean ismc = false;
    private boolean allied = false;
    private Bribe bribe = new Bribe();

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied) return;
        super.onOverlap(map, entity);
    }

    @Override
    public void interact(Player player, Game game) {
        if (player.getEntities(Sceptre.class).size() >= 1) {
            allied = true;
            mcduration = player.getEntities(Sceptre.class).get(0).getmcDuration();
            ismc = true;
            return;
        }
        allied = true;
        bribe.bribe(player, this);
    }

    @Override
    public void move(Game game) {
        bribe.allyMovement(game, this);
    }

    @Override
    public boolean isInteractable(Player player) {
        return !allied && bribe.canBeBribed(player, this);
    }

    @Override
    public int getBribeRadius() {
        return bribeRadius;
    }

    @Override
    public int getBribeAmount() {
        return bribeAmount;
    }

    public void setMcduration(int mcduration) {
        this.mcduration = mcduration;
    }

    public int getMcduration() {
        return mcduration;
    }

    public boolean isIsmc() {
        return ismc;
    }

    public void setIsmc(boolean ismc) {
        this.ismc = ismc;
    }

    public void setAllied(boolean allied) {
        this.allied = allied;
    }

}
