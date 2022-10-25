package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;

public class PlayerState {
    private Player player;
    private boolean isInvincible = false;
    private boolean isInvisible = false;

    public PlayerState(Player player, boolean isInvincible, boolean isInvisible) {
        this.player = player;
        this.isInvincible = isInvincible;
        this.isInvisible = isInvisible;
    }

    public boolean isInvincible() {
        return isInvincible;
    };

    public boolean isInvisible() {
        return isInvisible;
    };

    public Player getPlayer() {
        return player;
    }

    public void transitionInvisible() {
        isInvincible = false;
        isInvisible = true;
    };

    public void transitionInvincible() {
        isInvincible = true;
        isInvisible = false;
    };
    
    public void transitionBase() {
        isInvincible = false;
        isInvincible = false;
    };
}
