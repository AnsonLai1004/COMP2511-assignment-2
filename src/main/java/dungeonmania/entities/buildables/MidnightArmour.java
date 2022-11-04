package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class MidnightArmour extends Buildable {

    private int armourAttack;
    private int armourDefence;

    public MidnightArmour(int armourAttack, int armourDefence) {
        super(null);
        this.armourAttack = armourAttack;
        this.armourDefence = armourDefence;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(
            0,
            armourAttack,
            armourDefence,
            1,
            1));
    }

    @Override
    public void use(Game game) {
    }

    @Override
    public int getDurability() {
        return 0;
    }

}
