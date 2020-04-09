package Model.Agent;

import Model.Game.RandomSingleton;
import Model.Terrain.AbstractTerrain;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class RangedAgent extends Agent {

    public RangedAgent(int hp, int damageReduction, int speed, int strength, int range, String color) {
        super(hp, damageReduction, speed, strength, range, color);
    }

    public abstract FightStatus attack(Agent enemy);

    protected Direction getDirection(Agent enemy) {
        int rand = RandomSingleton.getInstance().nextInt(2);
        if (this.getPosX() == enemy.getPosX()) {
            if (this.getPosY() > enemy.getPosY()) {
                switch (rand) {
                    case 1:
                        return Direction.TOP_RIGHT;
                    case 2:
                        return Direction.TOP_LEFT;
                    default:
                        return Direction.RIGHT;
                }
            } else {
                switch (rand) {
                    case 1:
                        return Direction.TOP_LEFT;
                    case 2:
                        return Direction.BOTTOM_LEFT;
                    default:
                        return Direction.LEFT;
                }
            }
        }
        if (this.getPosX() > enemy.getPosX()) {
            switch (rand) {
                case 1:
                    return Direction.TOP_LEFT;
                case 2:
                    return Direction.TOP_RIGHT;
                default:
                    return Direction.TOP;
            }
        } else {
            switch (rand) {
                case 1:
                    return Direction.BOTTOM_RIGHT;
                case 2:
                    return Direction.BOTTOM_LEFT;
                default:
                    return Direction.BOT;
            }
        }
    }

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
        // CHeck TOP
        if (!terrain.isOutOfBounds(this.getPosX() + this.getRange(), this.getPosY()) && !terrain.isFree(this.getPosX() + this.getRange(), this.getPosY())) {
            if (enemyTeam.contains(terrain.agents[this.getPosX() + this.getRange()][this.getPosY()])) {
                atRange.add(terrain.agents[this.getPosX() + this.getRange()][this.getPosY()]);
            }
        }
        // Special range
        // Check TOP_LEFT
        if (!terrain.isOutOfBounds(this.getPosX() + 1, this.getPosY() - 1) && !terrain.isFree(this.getPosX() + 1, this.getPosY() - 1)) {
            if (enemyTeam.contains(terrain.agents[this.getPosX() + 1][this.getPosY() - 1])) {
                atRange.add(terrain.agents[this.getPosX() + 1][this.getPosY() - 1]);
            }
        }
        // Check TOP_RIGHT
        if (!terrain.isOutOfBounds(this.getPosX() + 1, this.getPosY() + 1) && !terrain.isFree(this.getPosX() + 1, this.getPosY() + 1)) {
            if (enemyTeam.contains(terrain.agents[this.getPosX() + 1][this.getPosY() + 1])) {
                atRange.add(terrain.agents[this.getPosX() + 1][this.getPosY() + 1]);
            }
        }
        // Check BOTTOM_LEFT
        if (!terrain.isOutOfBounds(this.getPosX() - 1, this.getPosY() - 1) && !terrain.isFree(this.getPosX() - 1, this.getPosY() - 1)) {
            if (enemyTeam.contains(terrain.agents[this.getPosX() - 1][this.getPosY() - 1])) {
                atRange.add(terrain.agents[this.getPosX() - 1][this.getPosY() - 1]);
            }
        }
        // Check BOTTOM_RIGHT
        if (!terrain.isOutOfBounds(this.getPosX() - 1, this.getPosY() + 1) && !terrain.isFree(this.getPosX() - 1, this.getPosY() + 1)) {
            if (enemyTeam.contains(terrain.agents[this.getPosX() - 1][this.getPosY() + 1])) {
                atRange.add(terrain.agents[this.getPosX() - 1][this.getPosY() + 1]);
            }
        }

        return atRange;
    }
}
