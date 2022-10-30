package dungeonmania.entities.enemies;

import java.util.Random;

import dungeonmania.Game;
import dungeonmania.entities.Bribe;
import dungeonmania.entities.Bribeable;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Assassin extends Enemy implements Interactable, Bribeable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;
    public static final double DEFAULT_BRIBE_FAIL_RATE = 0.3;

    private int bribeAmount = Assassin.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Assassin.DEFAULT_BRIBE_RADIUS;
    private double bribeFailRate = Assassin.DEFAULT_BRIBE_FAIL_RATE;
    private boolean allied = false;
    private Bribe bribe = new Bribe();
    private Random random = new Random(System.currentTimeMillis());

    public Assassin(Position position, double health, double attack,
            int bribeAmount, int bribeRadius, double bribeFailRate) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.bribeFailRate = bribeFailRate;
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
        bribe.bribe(player, this);
        if (random.nextInt(10) <= bribeFailRate * 10) {
            // fails
            allied = false;
        } else {
            allied = true;
        }
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

}
