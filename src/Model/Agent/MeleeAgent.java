package Model.Agent;

import Model.Game.RandomSingleton;
import Model.Terrain.AbstractTerrain;

import java.util.LinkedList;
import java.util.List;

/**
 * Melee Agent : Axe, Sword, Lance
 */
public abstract class MeleeAgent extends Agent {

    /**
     * MeleeAgent constructor
     *
     * @param hp              health points
     * @param damageReduction armor
     * @param speed           speed of movement
     * @param strength        strength
     * @param range           range of the agent
     * @param color           color of the team
     */
    public MeleeAgent(int hp, int damageReduction, int speed, int strength, int range, String color) {
        super(hp, damageReduction, speed, strength, range, color);
    }

    /**
     * Get a random direction to attack the focused enemy
     *
     * @param enemy the focused enemy
     * @return the chosen direction
     */
    protected Direction getDirection(Agent enemy) {
        int rand = RandomSingleton.getInstance().nextInt(2);
        if (this.getPosX() == enemy.getPosX()) {
            if (this.getPosY() > enemy.getPosY()) {
                switch (rand) {
                    case 1:
                        return Direction.TOP;
                    case 2:
                        return Direction.BOT;
                    default:
                        return Direction.RIGHT;
                }
            } else {
                switch (rand) {
                    case 1:
                        return Direction.TOP;
                    case 2:
                        return Direction.BOT;
                    default:
                        return Direction.LEFT;
                }
            }
        }
        if (this.getPosX() > enemy.getPosX()) {
            switch (rand) {
                case 1:
                    return Direction.LEFT;
                case 2:
                    return Direction.RIGHT;
                default:
                    return Direction.TOP;
            }
        } else {
            switch (rand) {
                case 1:
                    return Direction.RIGHT;
                case 2:
                    return Direction.LEFT;
                default:
                    return Direction.BOT;
            }
        }
    }

    /**
     * Find all enemies at range (distance between enemy and agent = 1)
     *
     * @param terrain   the terrain
     * @param enemyTeam the enemy team
     * @return List<Agent> the list of enemies at Range (up to 4)
     */
    protected List<Agent> findEnemiesAtRange(AbstractTerrain terrain, List<Agent> enemyTeam) {
        List<Agent> atRange = new LinkedList<>();

        // Check LEFT
        if (!terrain.isOutOfBounds(this.getPosX(), this.getPosY() - this.getRange()) && !terrain.isFree(this.getPosX(), this.getPosY() - this.getRange())) {
            if (enemyTeam.contains(terrain.agents[this.getPosX()][this.getPosY() - this.getRange()])) {
                atRange.add(terrain.agents[this.getPosX()][this.getPosY() - this.getRange()]);
            }
        }
        // Check RIGHT
        if (!terrain.isOutOfBounds(this.getPosX(), this.getPosY() + this.getRange()) && !terrain.isFree(this.getPosX(), this.getPosY() + this.getRange())) {
            if (enemyTeam.contains(terrain.agents[this.getPosX()][this.getPosY() + this.getRange()])) {
                atRange.add(terrain.agents[this.getPosX()][this.getPosY() + this.getRange()]);
            }
        }
        // Check BOT
        if (!terrain.isOutOfBounds(this.getPosX() - this.getRange(), this.getPosY()) && !terrain.isFree(this.getPosX() - this.getRange(), this.getPosY())) {
            if (enemyTeam.contains(terrain.agents[this.getPosX() - this.getRange()][this.getPosY()])) {
                atRange.add(terrain.agents[this.getPosX() - this.getRange()][this.getPosY()]);
            }
        }
        // Check TOP
        if (!terrain.isOutOfBounds(this.getPosX() + this.getRange(), this.getPosY()) && !terrain.isFree(this.getPosX() + this.getRange(), this.getPosY())) {
            if (enemyTeam.contains(terrain.agents[this.getPosX() + this.getRange()][this.getPosY()])) {
                atRange.add(terrain.agents[this.getPosX() + this.getRange()][this.getPosY()]);
            }
        }

        return atRange;
    }

    /**
     * Tells if the agent can counter attack
     *
     * @param enemy the enemy to counter attack
     * @return true if the distance between the agent and enemy = 1, false else
     */
    protected boolean canCounterAttack(Agent enemy) {
        boolean canCounterAttack = false;
        if (enemy instanceof MeleeAgent) {
            canCounterAttack = true;
        }
        return canCounterAttack;
    }

    protected abstract List<Agent> findBestTargets(List<Agent> targets, AbstractTerrain terrain);

    protected abstract int calculateDamage(Agent enemy);
}
