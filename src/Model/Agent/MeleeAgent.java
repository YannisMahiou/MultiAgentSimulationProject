package Model.Agent;

import Model.Terrain.AbstractTerrain;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by yannis on 14/01/20.
 */
public abstract class MeleeAgent extends Agent{

    public MeleeAgent(int hp, int damageReduction, int speed, int strength, int range, String color) {
        super(hp, damageReduction, speed, strength, range, color);
    }

    public abstract FightStatus attack(Agent enemy);

    @Override
    protected Direction getDirection(Agent enemy) {
        Random r = new Random();
        int rand = r.nextInt(2);
        if(this.getPosX() == enemy.getPosX()){
            if(this.getPosY() > enemy.getPosY()){
                switch (rand){
                    case 1:
                        return Direction.TOP;
                    case 2:
                        return Direction.BOT;
                    default:
                        return Direction.RIGHT;
                }
            }
            else {
                switch (rand){
                    case 1:
                        return Direction.TOP;
                    case 2:
                        return Direction.BOT;
                    default:
                        return Direction.LEFT;
                }
            }
        }
        if(this.getPosX() > enemy.getPosX()){
            switch (rand){
                case 1:
                    return Direction.LEFT;
                case 2:
                    return Direction.RIGHT;
                default :
                    return Direction.TOP;
            }
        }
        else {
            switch (rand){
                case 1:
                    return Direction.RIGHT;
                case 2:
                    return Direction.LEFT;
                default:
                    return Direction.BOT;
            }
        }
    }

    protected List<Agent> findEnemiesAtRange(AbstractTerrain terrain, List<Agent> enemyTeam){
        List<Agent> atRange = new LinkedList<>();

        // Check LEFT
        if (!terrain.isOutOfBounds(this.getPosX(), this.getPosY() - this.getRange()) && !terrain.isFree(this.getPosX(), this.getPosY() - this.getRange())) {
            if(enemyTeam.contains(terrain.agents[this.getPosX()][this.getPosY() - this.getRange()])){
                atRange.add(terrain.agents[this.getPosX()][this.getPosY() - this.getRange()]);
            }
        }
        // Check RIGHT
        if (!terrain.isOutOfBounds(this.getPosX(), this.getPosY() + this.getRange()) && !terrain.isFree(this.getPosX(), this.getPosY() + this.getRange())) {
            if(enemyTeam.contains(terrain.agents[this.getPosX()][this.getPosY() + this.getRange()])){
                atRange.add(terrain.agents[this.getPosX()][this.getPosY() + this.getRange()]);
            }
        }
        // Check BOT
        if (!terrain.isOutOfBounds(this.getPosX() - this.getRange(), this.getPosY())  && !terrain.isFree(this.getPosX() - this.getRange(), this.getPosY())) {
            if(enemyTeam.contains(terrain.agents[this.getPosX() - this.getRange()][this.getPosY()])){
                atRange.add(terrain.agents[this.getPosX() - this.getRange()][this.getPosY()]);
            }
        }
        // Check TOP
        if (!terrain.isOutOfBounds(this.getPosX() + this.getRange(), this.getPosY()) && !terrain.isFree(this.getPosX() + this.getRange(), this.getPosY())) {
            if(enemyTeam.contains(terrain.agents[this.getPosX() + this.getRange()][this.getPosY()])){
                atRange.add(terrain.agents[this.getPosX() + this.getRange()][this.getPosY()]);
            }
        }

        return atRange;
    }
}
