package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class Sceptre extends Buildable {

    private int mcduration;

    public Sceptre(int mcduration) {
        super(null);
        this.mcduration = mcduration;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(
            0,
            0,
            0,
            1,
            1));
    }

    @Override
    public void use(Game game) {

    }

    public int getmcDuration() {
        return mcduration;
    }

    @Override
    public int getDurability() {
        return 0;
    }
}
